package ru.tfs.spring.hw.qr_storage_service.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import ru.tfs.spring.hw.qr_storage_service.dto.VaccinationKafkaDto;

import java.nio.charset.StandardCharsets;

@Slf4j
public class QrHelper {

    public static String generateQr(VaccinationKafkaDto vaccination) {
        String codeToEncode = StringUtils.joinWith("_", vaccination.getDocumentNumber(),
                vaccination.getVaccineNumber(), vaccination.getVaccineName(), vaccination.getVaccinationDate());
        String generatedQr = DigestUtils.md5DigestAsHex(codeToEncode.getBytes(StandardCharsets.UTF_8));
        log.debug("Code to encode - {}. Generated qr - {}", codeToEncode, generatedQr);
        return generatedQr;
    }
}
