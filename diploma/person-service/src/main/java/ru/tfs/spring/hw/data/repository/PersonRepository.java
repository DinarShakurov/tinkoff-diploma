package ru.tfs.spring.hw.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tfs.spring.hw.data.entity.DocumentType;
import ru.tfs.spring.hw.data.entity.Person;

import java.util.Collection;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @EntityGraph(attributePaths = {"personToAddress.address.region", "identityDocuments", "contacts"})
    @Query("SELECT p FROM Person p WHERE p.personId = :id ")
    Optional<Person> findByIdFull(@Param("id") Long id);


    @EntityGraph(attributePaths = {"personToAddress.address.region", "identityDocuments", "contacts"})
    @Query("SELECT p FROM Person p JOIN p.identityDocuments idd " +
            "WHERE idd.documentType=:documentType AND idd.number=:documentNumber")
    Optional<Person> findByDocument(@Param("documentType") DocumentType documentType,
                                    @Param("documentNumber") String documentNumber);

    Page<Person> findByDeletedIsFalse(Pageable pageable);

    Page<Person> findDistinctByDeletedIsFalseAndPersonIdIn(Collection<Long> personIds, Pageable pageable);

    @Query("select distinct p from Person p join p.identityDocuments idd " +
            "where p.firstName=:firstName and p.lastName=:lastName and p.patronymic=:patronymic and " +
            "idd.number=:docNumber and idd.documentType=:docType")
    Page<Person> findByFIOAndPassport(@Param("firstName") String firstName,
                                      @Param("lastName") String lastName,
                                      @Param("patronymic") String patronymic,
                                      @Param("docNumber") String docNumber,
                                      @Param("docType") DocumentType docType, Pageable pageable);

}
