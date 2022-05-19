package ru.tfs.spring.hw.data.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tfs.spring.hw.data.dto.AddressDto;
import ru.tfs.spring.hw.data.dto.ContactDto;
import ru.tfs.spring.hw.data.dto.IdentityDocumentDto;
import ru.tfs.spring.hw.data.dto.PersonDto;
import ru.tfs.spring.hw.data.dto.PrettyPersonDto;
import ru.tfs.spring.hw.data.entity.AddressType;
import ru.tfs.spring.hw.data.entity.Contact;
import ru.tfs.spring.hw.data.entity.ContactType;
import ru.tfs.spring.hw.data.entity.IdentityDocument;
import ru.tfs.spring.hw.data.entity.Person;
import ru.tfs.spring.hw.data.entity.PersonToAddress;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {
    @Autowired
    protected AddressMapper addressMapper;
    @Autowired
    protected ContactMapper contactMapper;
    @Autowired
    protected IdentityDocumentMapper identityDocumentMapper;

    @Mapping(target = "addresses", expression = "java(addressMapper.mapToAddressesDto(person.getPersonToAddress()))")
    @Named("mapToPersonDto")
    public abstract PersonDto map(Person person);

    @IterableMapping(qualifiedByName = "mapToPersonDto")
    public abstract Set<PersonDto> map(Set<Person> persons);

    @Mapping(target = "phone", expression = "java(getPhone(person.getContacts()))")
    @Mapping(target = "document", expression = "java(getPrimaryDoc(person.getIdentityDocuments()))")
    @Mapping(target = "registrationAddress", expression = "java(getRegistrationAddress(person.getPersonToAddress()))")
    @Mapping(target = "fio", expression = "java(fioFromPerson(person))")
    @Named("mapToPrettyPersonDto")
    public abstract PrettyPersonDto mapPretty(Person person);

    @IterableMapping(qualifiedByName = "mapToPrettyPersonDto")
    public abstract List<PrettyPersonDto> mapPretty(List<Person> persons);


    protected String fioFromPerson(Person p) {
        return StringUtils.joinWith(" ", p.getLastName(), p.getFirstName(), p.getPatronymic());
    }

    protected ContactDto getPhone(Collection<Contact> contacts) {
        return contacts.stream()
                .filter(contact -> ContactType.PHONE_NUMBER.equals(contact.getContactType()))
                .map(contactMapper::map)
                .findFirst()
                .orElse(null);
    }

    protected IdentityDocumentDto getPrimaryDoc(Collection<IdentityDocument> identityDocuments) {
        return identityDocuments.stream()
                .filter(IdentityDocument::isPrimary)
                .map(identityDocumentMapper::map)
                .findFirst()
                .orElse(null);
    }

    protected AddressDto getRegistrationAddress(Collection<PersonToAddress> personToAddresses) {
        return personToAddresses.stream()
                .filter(pta -> AddressType.REGISTRATION.equals(pta.getAddressType()))
                .map(PersonToAddress::getAddress)
                .map(addressMapper::mapToAddressDto)
                .peek(addressDto -> addressDto.setAddressType(AddressType.REGISTRATION))
                .findFirst()
                .orElse(null);
    }
}
