package ru.tfs.spring.hw.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.hw.data.entity.DocumentType;
import ru.tfs.spring.hw.data.entity.IdentityDocument;

import java.util.Optional;

public interface IdentityDocumentRepository extends JpaRepository<IdentityDocument, Long> {

    Optional<IdentityDocument> findByDocumentTypeAndNumber(DocumentType type, String number);
}
