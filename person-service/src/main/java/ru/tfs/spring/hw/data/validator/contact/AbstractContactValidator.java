package ru.tfs.spring.hw.data.validator.contact;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tfs.spring.hw.data.dto.ContactDto;
import ru.tfs.spring.hw.data.entity.ContactType;
import ru.tfs.spring.hw.data.exception.ErrorCode;
import ru.tfs.spring.hw.data.services.contact.ContactValidatorService;
import ru.tfs.spring.hw.data.validator.Validator;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.ObjectUtils.allNull;
import static ru.tfs.spring.hw.data.exception.CommonException.buildException;

public abstract class AbstractContactValidator implements Validator<ContactDto> {

    abstract public ContactType getContactType();

    @Override
    public void validate(ContactDto dto) {
        if (
                !(allNotNull(dto.getContactId(), dto.getPersonId()) || allNull(dto.getContactId(), dto.getPersonId()))
        ) {
            throw buildException("error.validation.default", ErrorCode.VALIDATION_ERROR);
        }
    }

    @Autowired
    void registerMyself(ContactValidatorService contactValidatorService) {
        contactValidatorService.registerValidator(this, getContactType());
    }
}
