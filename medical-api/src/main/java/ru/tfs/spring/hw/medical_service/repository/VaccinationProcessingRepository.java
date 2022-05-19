package ru.tfs.spring.hw.medical_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.medical_service.entity.Vaccination;
import ru.tfs.spring.hw.medical_service.entity.VaccinationProcessing;
import ru.tfs.spring.hw.medical_service.entity.VaccinationProcessingStatus;

public interface VaccinationProcessingRepository extends JpaRepository<VaccinationProcessing, Vaccination> {

    Page<VaccinationProcessing> findAllByStatus(VaccinationProcessingStatus status, Pageable pageable);

    @Modifying
    @Query("UPDATE VaccinationProcessing v SET v.status = :status WHERE v.vaccinationId = :vaccinationId")
    @Transactional
    void setNewStatusByVaccinationId(@Param("vaccinationId") Long vaccinationId,
                                     @Param("status") VaccinationProcessingStatus status);
}
