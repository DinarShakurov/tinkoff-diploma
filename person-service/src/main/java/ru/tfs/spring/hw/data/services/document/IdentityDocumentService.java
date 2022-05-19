package ru.tfs.spring.hw.data.services.document;

import ru.tfs.spring.hw.data.dto.IdentityDocumentDto;
import ru.tfs.spring.hw.data.entity.IdentityDocument;
import ru.tfs.spring.hw.data.entity.Person;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IdentityDocumentService {

    Set<IdentityDocument> createOrUpdateAll(Set<IdentityDocumentDto> dtos, Person person);

}
