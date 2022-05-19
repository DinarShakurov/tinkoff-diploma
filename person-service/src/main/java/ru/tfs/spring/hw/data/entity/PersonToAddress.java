package ru.tfs.spring.hw.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "person_to_address", uniqueConstraints = @UniqueConstraint(columnNames = {"person_id", "address_id"}))
@Getter
@Setter
public class PersonToAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_to_address_id_seq")
    @SequenceGenerator(name = "person_to_address_id_seq", sequenceName = "person_to_address_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "address_type")
    @Enumerated(STRING)
    private AddressType addressType;
}
