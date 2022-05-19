package ru.tfs.spring.hw.data.services.person;

import org.springframework.data.domain.Pageable;
import ru.tfs.spring.hw.data.dto.PersonDto;
import ru.tfs.spring.hw.data.dto.PrettyPersonDto;
import ru.tfs.spring.hw.data.entity.DocumentType;
import ru.tfs.spring.hw.data.entity.Person;

import java.util.List;

public interface PersonService {

    Person update(PersonDto personDto);

    Person create(PersonDto personDto);

    PersonDto getById(Long personId);

    PersonDto findByDocument(DocumentType documentType, String document);

    List<PrettyPersonDto> findActivePersons(Pageable pageable, String region);

    boolean verifyNameAndPassport(String name, String docNumber, DocumentType type);
}
