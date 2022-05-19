package ru.tfs.spring.hw.data.validator.document;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tfs.spring.hw.data.dto.IdentityDocumentDto;
import ru.tfs.spring.hw.data.entity.DocumentType;
import ru.tfs.spring.hw.data.exception.ErrorCode;
import ru.tfs.spring.hw.data.services.document.IdentityDocumentValidatorService;
import ru.tfs.spring.hw.data.validator.Validator;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.ObjectUtils.allNull;
import static ru.tfs.spring.hw.data.exception.CommonException.buildException;

public abstract class AbstractIdentityDocumentValidator implements Validator<IdentityDocumentDto> {

    abstract public DocumentType getDocumentType();

    @Override
    public void validate(IdentityDocumentDto dto) {
        if (
                !(allNotNull(dto.getDocumentId(), dto.getPersonId()) || allNull(dto.getDocumentId(), dto.getPersonId()))
        ) {
            throw buildException("error.valiation.default", ErrorCode.VALIDATION_ERROR);
        }
    }

    @Autowired
    void registerMyself(IdentityDocumentValidatorService validatorService) {
        validatorService.registerValidator(this, getDocumentType());
    }
}
