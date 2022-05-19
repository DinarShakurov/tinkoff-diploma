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
@Table(name = "vaccination_point")
@Data
public class VaccinationPoint {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vaccination_point_vaccination_point_id_seq")
    @SequenceGenerator(
            name = "vaccination_point_vaccination_point_id_seq",
            sequenceName = "vaccination_point_vaccination_point_id_seq")
    @Column(name = "vaccination_point_id")
    private Long vaccinationPointId;

    @Column(name = "vaccination_point_number", unique = true, nullable = false)
    private String vaccinationPointNumber;

    @Column(name = "vaccination_point_name", nullable = false)
    private String vaccinationPointName;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;
}
