package ru.tfs.spring.hw.medical_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.hw.medical_service.entity.Vaccine;

import java.util.Optional;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    Optional<Vaccine> findFirstByVaccineName(String vaccineName);
}
