package ru.tfs.spring.hw.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "region")
@Getter
@Setter
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_region_id_seq")
    @SequenceGenerator(name = "region_region_id_seq", sequenceName = "region_region_id_seq")
    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "region_code", nullable = false, unique = true)
    private String regionCode;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    @Column(name = "kladr_id", nullable = false, unique = true)
    private String kladrId;

    @Column(name = "fias_id", nullable = false, unique = true)
    private String fiasId;
}
