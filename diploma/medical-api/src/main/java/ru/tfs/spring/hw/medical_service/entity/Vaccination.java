package ru.tfs.spring.hw.medical_service.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "vaccination")
@Data
public class Vaccination {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vaccination_vaccination_id_seq")
    @SequenceGenerator(
            name = "vaccination_vaccination_id_seq",
            sequenceName = "vaccination_vaccination_id_seq")
    @Column(name = "vaccination_id")
    private Long vaccinationId;

    @Column(name = "vaccine_number", unique = true, nullable = false)
    private String vaccineNumber;

    @ManyToOne
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;

    @Column(name = "vaccination_date", nullable = false)
    private LocalDate vaccinationDate;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "patronymic", nullable = false)
    private String patronymic;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    @ManyToOne
    @JoinColumn(name = "vaccination_point_id", nullable = false)
    private VaccinationPoint vaccinationPoint;
}
