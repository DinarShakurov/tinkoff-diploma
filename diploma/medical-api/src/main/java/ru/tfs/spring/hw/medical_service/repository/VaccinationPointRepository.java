package ru.tfs.spring.hw.medical_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.hw.medical_service.entity.VaccinationPoint;

import java.util.Optional;

public interface VaccinationPointRepository extends JpaRepository<VaccinationPoint, Long> {

    Optional<VaccinationPoint> findFirstByVaccinationPointNumber(String vaccinationPointNumber);
}
