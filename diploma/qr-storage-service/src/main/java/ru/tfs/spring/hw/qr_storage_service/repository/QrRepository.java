package ru.tfs.spring.hw.qr_storage_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.hw.qr_storage_service.entity.Qr;

import java.util.Optional;

public interface QrRepository extends JpaRepository<Qr, Long> {

    Optional<Qr> findByVaccineNumber(String vaccineNumber);

    Optional<Qr> findFirstByDocumentOrderByCreateDateDesc(String document);

    Optional<Qr> findByQrCode(String qrCode);
}
