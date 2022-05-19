package ru.tfs.spring.hw.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_address_id_seq")
    @SequenceGenerator(name = "address_address_id_seq", sequenceName = "address_address_id_seq")
    @Column(name = "address_id")
    private Long addressId;

    @OneToMany(mappedBy = "address", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<PersonToAddress> personToAddress = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "locality", nullable = false)
    private String locality;

    @Column(name = "house_number", nullable = false)
    private String houseNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;
}
