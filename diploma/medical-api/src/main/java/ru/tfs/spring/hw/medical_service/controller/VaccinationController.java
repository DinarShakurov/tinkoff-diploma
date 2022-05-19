package ru.tfs.spring.hw.medical_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tfs.spring.hw.medical_service.common.JsonItem;
import ru.tfs.spring.hw.medical_service.common.JsonItemFactory;
import ru.tfs.spring.hw.medical_service.dto.VaccinationDto;
import ru.tfs.spring.hw.medical_service.service.VaccinationService;

@RestController
@RequestMapping("/api/public/vaccination")
@RequiredArgsConstructor
@Slf4j
public class VaccinationController {

    private final JsonItemFactory jsonItemFactory;
    private final VaccinationService vaccinationService;

    @GetMapping
    public JsonItem<VaccinationDto> getVaccination(@RequestParam String document) {
        return jsonItemFactory.successResult(vaccinationService.getVaccination(document));
    }
}
