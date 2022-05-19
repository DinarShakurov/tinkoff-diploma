package ru.tfs.spring.hw.data.entity;

import lombok.Getter;
import lombok.Setter;
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
@Table(name = "identity_document", uniqueConstraints = @UniqueConstraint(columnNames = {"document_type", "document_number"}))
@Getter
@Setter
public class IdentityDocument extends AuditedBusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identity_document_identity_document_id_seq")
    @SequenceGenerator(name = "identity_document_identity_document_id_seq",
            sequenceName = "identity_document_identity_document_id_seq")
    @Column(name = "identity_document_id")
    private Long documentId;

    @Column(name = "document_type", nullable = false)
    @Enumerated(STRING)
    private DocumentType documentType;

    @Column(name = "document_number", nullable = false)
    private String number;

    @Column(name = "is_primary", nullable = false)
    private boolean primary;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
