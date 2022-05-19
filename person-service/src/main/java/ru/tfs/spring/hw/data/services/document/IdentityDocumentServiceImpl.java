package ru.tfs.spring.hw.data.services.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.data.dto.IdentityDocumentDto;
import ru.tfs.spring.hw.data.entity.IdentityDocument;
import ru.tfs.spring.hw.data.entity.Person;
import ru.tfs.spring.hw.data.exception.ErrorCode;
import ru.tfs.spring.hw.data.repository.IdentityDocumentRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static ru.tfs.spring.hw.data.exception.CommonException.buildException;

@Service
@RequiredArgsConstructor
@Slf4j
public class IdentityDocumentServiceImpl implements IdentityDocumentService {
    private final IdentityDocumentValidatorService validator;
    private final IdentityDocumentRepository identityDocumentRepository;

    @Override
    @Transactional
    public Set<IdentityDocument> createOrUpdateAll(Set<IdentityDocumentDto> dtos, Person person) {
        validator.onlyOnePrimary(dtos);
        return dtos.stream()
                .map(contact -> createOrUpdate(contact, person))
                .collect(Collectors.toSet());
    }

    private IdentityDocument createOrUpdate(IdentityDocumentDto dto, Person person) {
        validator.validate(dto);

        IdentityDocument identityDocument = dto.getDocumentId() == null
                ? new IdentityDocument()
                : identityDocumentRepository.findById(dto.getDocumentId()).orElseThrow(
                () -> buildException("error.validation.document.notFound", ErrorCode.ENTITY_NOT_FOUND));

        validator.throwIfDuplicate(identityDocument.getDocumentId(), dto.getDocumentType(), dto.getNumber());

        identityDocument.setNumber(dto.getNumber());
        identityDocument.setDocumentType(dto.getDocumentType());
        identityDocument.setPerson(person);
        identityDocument.setPrimary(dto.isPrimary());
        return identityDocument;
    }
}
