package ru.tfs.spring.hw.data.services.contact;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tfs.spring.hw.data.dto.ContactDto;
import ru.tfs.spring.hw.data.entity.ContactType;
import ru.tfs.spring.hw.data.exception.ErrorCode;
import ru.tfs.spring.hw.data.repository.ContactRepository;
import ru.tfs.spring.hw.data.validator.Validator;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import static java.lang.String.format;
import static ru.tfs.spring.hw.data.exception.CommonException.buildException;
import static ru.tfs.spring.hw.data.exception.ErrorCode.VALIDATION_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactValidatorService {

    private final Map<ContactType, Validator<ContactDto>> validators = new EnumMap<>(ContactType.class);
    private final ContactRepository contactRepository;

    public void registerValidator(Validator<ContactDto> validator, ContactType contactType) {
        log.debug("Registering contact validator for {}. Validators count: {}", contactType, validators.size());
        validators.put(contactType, validator);
        log.debug("Successful contact validator registration for {}. Validators count: {}", contactType, validators.size());
    }

    public void validate(ContactDto ContactDto) {
        Validator<ContactDto> validator = validators.get(ContactDto.getContactType());
        if (validator != null) {
            validator.validate(ContactDto);
        }
    }

    public void atLeastOnePhone(Collection<ContactDto> ContactDtos) {
        if (ContactDtos.stream().noneMatch(contact -> ContactType.PHONE_NUMBER.equals(contact.getContactType()))) {
            throw buildException("error.validation.contact.noPhone", VALIDATION_ERROR);
        }
    }

    public void throwIfDuplicate(Long withId, ContactType type, String value) {
        contactRepository.findByContactTypeAndValue(type, value).ifPresent(contact -> {
            if (!contact.getContactId().equals(withId)) {
                throw buildException("error.validation.contact.alreadyExists", VALIDATION_ERROR, value);
            }
        });
    }
}
