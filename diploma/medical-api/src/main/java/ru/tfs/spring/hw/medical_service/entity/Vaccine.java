package ru.tfs.spring.hw.medical_service.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vaccine")
@Data
public class Vaccine {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vaccine_vaccine_id_seq")
    @SequenceGenerator(name = "vaccine_vaccine_id_seq", sequenceName = "vaccine_vaccine_id_seq", allocationSize = 10)
    @Column(name = "vaccine_id")
    private Long vaccineId;

    @Column(name = "vaccine_name", nullable = false, unique = true)
    private String vaccineName;
}
