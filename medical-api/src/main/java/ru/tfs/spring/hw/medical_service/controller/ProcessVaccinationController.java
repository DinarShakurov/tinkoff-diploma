package ru.tfs.spring.hw.medical_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tfs.spring.hw.medical_service.common.JsonItem;
import ru.tfs.spring.hw.medical_service.common.JsonItemFactory;
import ru.tfs.spring.hw.medical_service.service.VaccinationService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/private/vaccination")
@RequiredArgsConstructor
@Slf4j
public class ProcessVaccinationController {

    private final JsonItemFactory jsonItemFactory;
    private final VaccinationService vaccinationService;

    @PostMapping(path = "/process-file", consumes = {"text/csv"})
    public JsonItem processFile(HttpServletRequest request, @RequestBody String csvContent) {
        vaccinationService.processVaccinations(csvContent);
        return jsonItemFactory.successResult();
    }
}
