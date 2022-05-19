package ru.tfs.spring.hw.qr_storage_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.qr_storage_service.dto.QrDto;
import ru.tfs.spring.hw.qr_storage_service.dto.VaccinationKafkaDto;
import ru.tfs.spring.hw.qr_storage_service.entity.Qr;
import ru.tfs.spring.hw.qr_storage_service.exception.ErrorCode;
import ru.tfs.spring.hw.qr_storage_service.helper.QrHelper;
import ru.tfs.spring.hw.qr_storage_service.mapper.QrMapper;
import ru.tfs.spring.hw.qr_storage_service.repository.QrRepository;

import java.util.Optional;

import static ru.tfs.spring.hw.qr_storage_service.exception.CommonException.buildException;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrServiceImpl implements QrService {

    private final QrRepository qrRepository;
    private final QrMapper qrMapper;

    @Override
    @KafkaListener(topics = "${qr-storage-service.config.kafka.generate-qr-code-topic}")
    @Transactional
    public void handleNewVaccination(VaccinationKafkaDto vaccination) {
        log.info("New vaccination - {}", vaccination);
        qrRepository.findByVaccineNumber(vaccination.getVaccineNumber()).ifPresentOrElse(qr -> {
            log.error("Can't process new vaccination, because QR code for vaccineNumber-{} already generated. {}, {}",
                    vaccination.getVaccineNumber(), vaccination, qr);
        }, () -> {
            String qrCode = QrHelper.generateQr(vaccination);
            Qr qr = new Qr();
            qr.setDocument(vaccination.getDocumentNumber());
            qr.setVaccinationDate(vaccination.getVaccinationDate());
            qr.setVaccineName(vaccination.getVaccineName());
            qr.setVaccineNumber(vaccination.getVaccineNumber());
            qr.setQrCode(qrCode);

            qr = qrRepository.save(qr);
            log.debug("Saved qr - {}", qr);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public QrDto getQr(String document) {
        Qr qr = qrRepository.findFirstByDocumentOrderByCreateDateDesc(document)
                .orElseThrow(() -> buildException("error.qr.notFound", ErrorCode.ENTITY_NOT_FOUND));
        return qrMapper.map(qr);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyQr(String qrCode) {
        Optional<Qr> optionalQr = qrRepository.findByQrCode(qrCode);
        if (optionalQr.isEmpty()) {
            log.debug("QR-code invalid - {}", qrCode);
            return false;
        }
        Qr qr = optionalQr.get();
        Qr lastQr = qrRepository.findFirstByDocumentOrderByCreateDateDesc(qr.getDocument()).get();
        return qr.getCreateDate().equals(lastQr.getCreateDate());
    }
}
