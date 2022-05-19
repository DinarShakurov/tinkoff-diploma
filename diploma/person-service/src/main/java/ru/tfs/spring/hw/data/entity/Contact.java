package ru.tfs.spring.hw.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.tfs.spring.hw.data.entity.base.AuditedBusinessEntity;

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
@Table(name = "contact", uniqueConstraints = @UniqueConstraint(columnNames = {"contact_type", "contact_value"}))
@Getter
@Setter
@ToString(exclude = "person")
public class Contact extends AuditedBusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_contact_id_seq")
    @SequenceGenerator(name = "contact_contact_id_seq", sequenceName = "contact_contact_id_seq")
    @Column(name = "contact_id")
    private Long contactId;


    @Column(name = "contact_type", nullable = false)
    @Enumerated(STRING)
    private ContactType contactType;

    @Column(name = "contact_value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
