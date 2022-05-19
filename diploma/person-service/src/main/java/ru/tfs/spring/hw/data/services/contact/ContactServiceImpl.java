package ru.tfs.spring.hw.data.services.contact;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.data.exception.ErrorCode;
import ru.tfs.spring.hw.data.dto.ContactDto;
import ru.tfs.spring.hw.data.entity.Contact;
import ru.tfs.spring.hw.data.entity.Person;
import ru.tfs.spring.hw.data.repository.ContactRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static ru.tfs.spring.hw.data.exception.CommonException.buildException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactValidatorService validator;
    private final ContactRepository contactRepository;

    @Override
    @Transactional
    public Set<Contact> createOrUpdateAll(Set<ContactDto> ContactDtos, Person person) {
        validator.atLeastOnePhone(ContactDtos);
        return ContactDtos.stream()
                .map(contactDto -> findOrCreate(contactDto, person))
                .collect(Collectors.toSet());
    }

    private Contact findOrCreate(ContactDto dto, Person person) {
        validator.validate(dto);
        Contact contact = dto.getContactId() == null
                ? new Contact()
                : contactRepository.findById(dto.getContactId()).orElseThrow(() ->
                buildException("error.validation.contact.notFound", ErrorCode.ENTITY_NOT_FOUND));

        validator.throwIfDuplicate(contact.getContactId(), dto.getContactType(), dto.getValue());

        contact.setContactType(dto.getContactType());
        contact.setValue(dto.getValue());
        contact.setPerson(person);

        log.debug("Creating or updating contact - {}", contact);
        return contact;
    }

}
