package ru.tfs.spring.hw.medical_service.service;

import ru.tfs.spring.hw.medical_service.dto.VaccinationDto;

import java.util.List;

public interface VaccinationService {

    void processVaccinations(String csvContent);

    void processVaccinations(List<VaccinationDto> vaccinationDtoList);

    void processVaccination(VaccinationDto vaccinationDto);

    VaccinationDto getVaccination(String document);
}
