package ru.tfs.spring.hw.medical_service.service;

import com.opencsv.bean.BeanVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.medical_service.common.JsonItem;
import ru.tfs.spring.hw.medical_service.dto.VaccinationDto;
import ru.tfs.spring.hw.medical_service.entity.Vaccination;
import ru.tfs.spring.hw.medical_service.entity.VaccinationPoint;
import ru.tfs.spring.hw.medical_service.entity.VaccinationProcessing;
import ru.tfs.spring.hw.medical_service.entity.VaccinationProcessingStatus;
import ru.tfs.spring.hw.medical_service.entity.Vaccine;
import ru.tfs.spring.hw.medical_service.exception.ErrorCode;
import ru.tfs.spring.hw.medical_service.feign.PersonServiceClient;
import ru.tfs.spring.hw.medical_service.mapper.VaccinationMapper;
import ru.tfs.spring.hw.medical_service.repository.VaccinationPointRepository;
import ru.tfs.spring.hw.medical_service.repository.VaccinationProcessingRepository;
import ru.tfs.spring.hw.medical_service.repository.VaccinationRepository;
import ru.tfs.spring.hw.medical_service.repository.VaccineRepository;

import java.util.List;
import java.util.Optional;

import static ru.tfs.spring.hw.medical_service.csv.CsvHelper.readCsvToList;
import static ru.tfs.spring.hw.medical_service.exception.CommonException.buildException;

@Service
@Slf4j
@RequiredArgsConstructor
public class VaccinationServiceImpl implements VaccinationService {

    private final BeanVerifier<VaccinationDto> vaccinationDtoBeanVerifier;
    private final PersonServiceClient personServiceClient;
    private final VaccinationMapper mapper;
    private final VaccinationRepository vaccinationRepository;
    private final VaccinationPointRepository vaccinationPointRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccinationProcessingRepository vaccinationProcessingRepository;

    @Override
    @Transactional
    public void processVaccinations(String csvContent) {
        processVaccinations(readCsvToList(csvContent, VaccinationDto.class, vaccinationDtoBeanVerifier));
    }

    @Override
    @Transactional
    public void processVaccinations(List<VaccinationDto> vaccinationDtoList) {
        vaccinationDtoList.forEach(this::processVaccination);
    }

    @Override
    @Transactional
    public void processVaccination(VaccinationDto dto) {
        String fio = StringUtils.joinWith(" ", dto.getLastName(), dto.getFirstName(), dto.getPatronymic());
        JsonItem<Boolean> response = personServiceClient.verifyPerson(fio, dto.getDocumentNumber());
        if (response.isSuccess()) {
            if (response.getData()) {
                Optional<Vaccine> oVaccine = vaccineRepository.findFirstByVaccineName(dto.getVaccineName());
                if (oVaccine.isEmpty()) {
                    log.error("Vaccination {} can't be processed. Vaccine not found", dto);
                    return;
                }
                Optional<VaccinationPoint> oVaccinationPoint = vaccinationPointRepository
                        .findFirstByVaccinationPointNumber(dto.getVaccinationPointNumber());
                if (oVaccinationPoint.isEmpty()) {
                    log.error("Vaccination {} can't be processed. Vaccination Point not found", dto);
                    return;
                }
                Vaccination vaccination = new Vaccination();
                vaccination.setVaccinationPoint(oVaccinationPoint.get());
                vaccination.setVaccineNumber(dto.getVaccineNumber());
                vaccination.setVaccine(oVaccine.get());
                vaccination.setDocumentNumber(dto.getDocumentNumber());
                vaccination.setFirstName(dto.getFirstName());
                vaccination.setLastName(dto.getLastName());
                vaccination.setPatronymic(dto.getPatronymic());
                vaccination.setVaccinationDate(dto.getVaccinationDate());
                vaccination = vaccinationRepository.save(vaccination);
                vaccinationProcessingRepository.save(
                        new VaccinationProcessing(vaccination, VaccinationProcessingStatus.NEW)
                );
            } else {
                log.info("Person-Service: Person wasn't verified. Fio - {}, document - {}", fio, dto.getDocumentNumber());
            }
        } else {
            log.error("Person-Service: Response unsuccessful during verifying person. Message - {}. Stacktrace - {}",
                    response.getMessage(),
                    response.getStacktrace());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public VaccinationDto getVaccination(String document) {
        Vaccination vaccination = vaccinationRepository.findFirstByDocumentNumber(document).orElseThrow(() ->
                buildException("error.vaccination.notFound", ErrorCode.ENTITY_NOT_FOUND, document));
        return mapper.map(vaccination);
    }
}
