package ru.tfs.spring.hw.medical_service.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vaccination_processing")
@Data
@NoArgsConstructor
public class VaccinationProcessing {

    @Id
    @Column(name = "vaccination_id")
    private Long vaccinationId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "vaccination_id")
    private Vaccination vaccination;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private VaccinationProcessingStatus status;

    public VaccinationProcessing(Vaccination vaccination, VaccinationProcessingStatus status) {
        this.vaccination = vaccination;
        this.status = status;
    }
}
