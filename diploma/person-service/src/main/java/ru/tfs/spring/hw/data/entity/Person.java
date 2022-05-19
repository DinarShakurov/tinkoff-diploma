package ru.tfs.spring.hw.data.entity;

import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.hw.data.entity.base.SoftDeletableBusinessEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends SoftDeletableBusinessEntity {

    /**
     * <a href="https://stackoverflow.com/questions/27697810/why-does-hibernate-disable-insert-batching-when-using-an-identity-identifier-gen">https://stackoverflow.com/questions/27697810/why-does-hibernate-disable-insert-batching-when-using-an-identity-identifier-gen</a>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_person_id_seq")
    @SequenceGenerator(name = "person_person_id", sequenceName = "person_person_id_seq")
    @Column(name = "person_id")
    private Long personId;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<PersonToAddress> personToAddress = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<IdentityDocument> identityDocuments = new HashSet<>();

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
}
