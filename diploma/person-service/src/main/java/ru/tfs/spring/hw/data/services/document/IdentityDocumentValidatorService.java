package ru.tfs.spring.hw.data.services.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tfs.spring.hw.data.exception.ErrorCode;
import ru.tfs.spring.hw.data.dto.IdentityDocumentDto;
import ru.tfs.spring.hw.data.entity.DocumentType;
import ru.tfs.spring.hw.data.repository.IdentityDocumentRepository;
import ru.tfs.spring.hw.data.validator.Validator;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import static java.lang.String.format;
import static ru.tfs.spring.hw.data.exception.CommonException.buildException;

@Service
@Slf4j
@RequiredArgsConstructor
public class IdentityDocumentValidatorService {

    private final Map<DocumentType, Validator<IdentityDocumentDto>> validators = new EnumMap<>(DocumentType.class);
    private final IdentityDocumentRepository repository;

    public void registerValidator(Validator<IdentityDocumentDto> validator, DocumentType documentType) {
        log.debug("Registering document validator for {}. Validators count: {}", documentType, validators.size());
        validators.put(documentType, validator);
        log.debug("Successful document validator registration for {}. Validators count: {}", documentType, validators.size());
    }

    public void validate(IdentityDocumentDto identityDocumentDto) {
        Validator<IdentityDocumentDto> validator = validators.get(identityDocumentDto.getDocumentType());
        if (validator != null) {
            validator.validate(identityDocumentDto);
        }
    }

    public void onlyOnePrimary(Collection<IdentityDocumentDto> identityDocuments) {
        long count = identityDocuments.stream().filter(IdentityDocumentDto::isPrimary).count();
        if (count != 1) {
            throw buildException("error.validation.document.onlyOnePrimary", ErrorCode.VALIDATION_ERROR, count);
        }
    }

    public void throwIfDuplicate(Long withId, DocumentType type, String number) {
        repository.findByDocumentTypeAndNumber(type, number).ifPresent(document -> {
            if (!document.getDocumentId().equals(withId)) {
                throw buildException("error.validation.document.alreadyExists", ErrorCode.VALIDATION_ERROR, number);
            }
        });
    }
}
