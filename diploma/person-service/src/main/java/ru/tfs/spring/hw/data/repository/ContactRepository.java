package ru.tfs.spring.hw.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.hw.data.entity.Contact;
import ru.tfs.spring.hw.data.entity.ContactType;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByContactTypeAndValue(ContactType contactType, String contactValue);
}
