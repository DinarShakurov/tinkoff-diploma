package ru.tfs.spring.hw.medical_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.hw.medical_service.entity.Vaccination;

import java.util.Optional;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    Optional<Vaccination> findFirstByDocumentNumber(String document);
}
