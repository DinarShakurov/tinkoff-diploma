package ru.tfs.spring.hw.qr_storage_service.service;

import ru.tfs.spring.hw.qr_storage_service.dto.QrDto;
import ru.tfs.spring.hw.qr_storage_service.dto.VaccinationKafkaDto;

public interface QrService {

    void handleNewVaccination(VaccinationKafkaDto vaccination);

    QrDto getQr(String document);

    boolean verifyQr(String qr);
}
