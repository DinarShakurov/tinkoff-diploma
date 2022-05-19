package ru.tfs.spring.hw.medical_service.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class VaccinationDto {

    @CsvIgnore
    private Long vaccinationId;

    @CsvBindByName(column = "first_name", required = true)
    @NotBlank
    private String firstName;

    @CsvBindByName(column = "last_name")
    @NotBlank
    private String lastName;

    @CsvBindByName(column = "patronymic")
    private String patronymic;

    @CsvBindByName(column = "document_number")
    @NotBlank
    private String documentNumber;

    @CsvBindByName(column = "vaccination_date")
    @CsvDate("yyyy-MM-dd")
    @NotNull
    private LocalDate vaccinationDate;

    @CsvBindByName(column = "vaccine_name")
    @NotBlank
    private String vaccineName;

    @CsvBindByName(column = "vaccine_number")
    @NotBlank
    private String vaccineNumber;

    @CsvBindByName(column = "vaccination_point_name")
    @NotBlank
    private String vaccinationPointName;

    @CsvBindByName(column = "vaccination_point_number")
    @NotBlank
    private String vaccinationPointNumber;

    @CsvBindByName(column = "city")
    @NotBlank
    private String city;

    @CsvBindByName(column = "address")
    @NotBlank
    private String address;
}
