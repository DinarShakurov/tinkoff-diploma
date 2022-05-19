package ru.tfs.spring.hw.medical_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import ru.tfs.spring.hw.medical_service.dto.VaccinationKafkaDto;
import ru.tfs.spring.hw.medical_service.entity.VaccinationProcessing;
import ru.tfs.spring.hw.medical_service.mapper.VaccinationMapper;
import ru.tfs.spring.hw.medical_service.repository.VaccinationProcessingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static ru.tfs.spring.hw.medical_service.entity.VaccinationProcessingStatus.NEW;
import static ru.tfs.spring.hw.medical_service.entity.VaccinationProcessingStatus.SENT;

@Service
@Slf4j
@RequiredArgsConstructor
public class VaccinationProcessingServiceImpl implements VaccinationProcessingService {

    private final VaccinationProcessingRepository vaccinationProcessingRepository;
    private final KafkaTemplate<Long, VaccinationKafkaDto> kafkaTemplate;
    private final VaccinationMapper vaccinationMapper;

    @Override
    @Transactional
    public void processNewVaccinations() {
        Pageable pageRequest = PageRequest.of(0, 50);
        Page<VaccinationProcessing> page = vaccinationProcessingRepository.findAllByStatus(NEW, pageRequest);
        List<ListenableFuture<SendResult<Long, VaccinationKafkaDto>>> futureResult = new ArrayList<>();

        log.debug("Found NEW vaccinations - {}", page.getTotalElements());

        while (!page.isEmpty()) {
            futureResult.addAll(page.stream()
                    .map(VaccinationProcessing::getVaccination)
                    .map(vaccination -> {
                        VaccinationKafkaDto vaccinationKafkaDto = vaccinationMapper.mapToKafka(vaccination);
                        log.debug("Sending {} to topic '{}'", vaccinationKafkaDto, kafkaTemplate.getDefaultTopic());
                        return kafkaTemplate.sendDefault(
                                vaccination.getVaccinationId(),
                                vaccinationKafkaDto
                        );
                    })
                    .toList()
            );
            pageRequest = pageRequest.next();
            page = vaccinationProcessingRepository.findAllByStatus(NEW, pageRequest);
        }

        for (ListenableFuture<SendResult<Long, VaccinationKafkaDto>> sendResultListenableFuture : futureResult) {
            try {
                Long vaccinationId = sendResultListenableFuture.get().getProducerRecord().key();
                log.debug("Kafka success: updating status for vaccination with id - {}", vaccinationId);
                vaccinationProcessingRepository.setNewStatusByVaccinationId(vaccinationId, SENT);
            } catch (InterruptedException | ExecutionException e) {
                log.error("KafkaError", e);
            }
        }
    }
}
