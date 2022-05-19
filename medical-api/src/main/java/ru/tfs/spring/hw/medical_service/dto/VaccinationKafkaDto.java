package ru.tfs.spring.hw.medical_service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VaccinationKafkaDto implements Serializable {

    private Long vaccinationId;
    private String vaccineNumber;
    private String vaccineName;
    private String documentNumber;
    private LocalDate vaccinationDate;
}
