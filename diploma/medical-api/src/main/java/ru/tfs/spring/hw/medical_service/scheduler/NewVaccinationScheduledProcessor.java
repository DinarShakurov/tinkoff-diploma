package ru.tfs.spring.hw.medical_service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tfs.spring.hw.medical_service.service.VaccinationProcessingService;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewVaccinationScheduledProcessor {

    private final VaccinationProcessingService vaccinationProcessingService;

    @Scheduled(fixedDelayString = "${medical-service.config.new-vaccination-fixed-delay}")
    @SchedulerLock(name = "processNewVaccinations", lockAtMostFor = "5m")
    public void processNewVaccinations() {
        log.info("Job started. Processing new vaccinations");
        vaccinationProcessingService.processNewVaccinations();
        log.info("Job is done. New vaccinations processed");
    }
}
