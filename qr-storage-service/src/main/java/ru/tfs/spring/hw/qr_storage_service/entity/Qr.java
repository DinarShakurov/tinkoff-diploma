package ru.tfs.spring.hw.qr_storage_service.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "qr")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Qr {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qr_qr_id_seq")
    @SequenceGenerator(name = "qr_qr_id_seq", sequenceName = "qr_qr_id_seq")
    @Column(name = "qr_id")
    private Long qrId;

    @Column(name = "qr_code", nullable = false, unique = true)
    private String qrCode;

    @Column(name = "document_number", nullable = false)
    private String document;

    @Column(name = "vaccine_number", nullable = false, unique = true)
    private String vaccineNumber;

    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

    @Column(name = "vaccination_date", nullable = false)
    private LocalDate vaccinationDate;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;
}
