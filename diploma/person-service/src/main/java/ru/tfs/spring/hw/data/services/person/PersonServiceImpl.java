package ru.tfs.spring.hw.data.services.person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.data.dto.PersonDto;
import ru.tfs.spring.hw.data.dto.PrettyPersonDto;
import ru.tfs.spring.hw.data.entity.DocumentType;
import ru.tfs.spring.hw.data.entity.Person;
import ru.tfs.spring.hw.data.exception.ErrorCode;
import ru.tfs.spring.hw.data.mapper.PersonMapper;
import ru.tfs.spring.hw.data.repository.PersonRepository;
import ru.tfs.spring.hw.data.repository.PersonToAddressRepository;
import ru.tfs.spring.hw.data.services.address.AddressService;
import ru.tfs.spring.hw.data.services.contact.ContactService;
import ru.tfs.spring.hw.data.services.document.IdentityDocumentService;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static ru.tfs.spring.hw.data.exception.CommonException.buildException;
import static ru.tfs.spring.hw.data.exception.ErrorCode.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final AddressService addressService;
    private final ContactService contactService;
    private final IdentityDocumentService identityDocumentService;

    private final PersonMapper mapper;

    private final PersonRepository personRepository;
    private final PersonToAddressRepository personToAddressRepository;

    @Override
    @Transactional
    public Person update(PersonDto dto) {
        if (dto.getPersonId() == null) {
            throw buildException("error.validation.default", ErrorCode.VALIDATION_ERROR);
        }
        Person person = personRepository.findById(dto.getPersonId()).orElseThrow(() ->
                buildException("error.validation.person.notFound", ENTITY_NOT_FOUND));
        return createOrUpdate(dto, person);
    }

    @Override
    @Transactional
    public Person create(PersonDto personDto) {
        return createOrUpdate(personDto, new Person());
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto getById(Long personId) {
        return personRepository.findByIdFull(personId)
                .map(mapper::map)
                .orElseThrow(() -> buildException("error.validation.person.notFound", ENTITY_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findByDocument(DocumentType documentType, String document) {
        return personRepository.findByDocument(DocumentType.PASSPORT, document)
                .map(mapper::map)
                .orElseThrow(() -> buildException("error.validation.person.notFound", ENTITY_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrettyPersonDto> findActivePersons(Pageable pageable, String region) {
        Page<Person> persons;
        if (StringUtils.isNotBlank(region)) {
            Set<Long> ids = personToAddressRepository.personIdsByRegionName(region);
            persons = personRepository.findDistinctByDeletedIsFalseAndPersonIdIn(ids, pageable);
        } else {
            persons = personRepository.findByDeletedIsFalse(pageable);
        }
        return mapper.mapPretty(persons.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyNameAndPassport(String name, String docNumber, DocumentType type) {
        String[] fio = name.split(" ");
        String firstName = null;
        String lastName = null;
        String patronymic = null;
        if (fio.length > 0) {
            lastName = fio[0];
            if (fio.length > 1) {
                firstName = fio[1];
                if (fio.length > 2) {
                    patronymic = fio[2];
                }
            }
        }
        return !personRepository.findByFIOAndPassport(firstName, lastName, patronymic, docNumber,
                DocumentType.PASSPORT, PageRequest.of(0, 2)).isEmpty();
    }

    private Person createOrUpdate(PersonDto dto, Person person) {
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setPatronymic(dto.getPatronymic());
        person.setPersonToAddress(addressService.createForPerson(dto.getAddresses(), person));
        person.setContacts(contactService.createOrUpdateAll(dto.getContacts(), person));
        person.setIdentityDocuments(
                identityDocumentService.createOrUpdateAll(dto.getIdentityDocuments(), person)
        );
        person.setBirthDate(dto.getBirthDate());
        person.setDeleted(dto.isDeleted());
        return personRepository.save(person);
    }
}
