package ru.tfs.spring.hw.data.services.contact;

import ru.tfs.spring.hw.data.dto.ContactDto;
import ru.tfs.spring.hw.data.entity.Contact;
import ru.tfs.spring.hw.data.entity.Person;

import java.util.List;
import java.util.Set;

public interface ContactService {

    Set<Contact> createOrUpdateAll(Set<ContactDto> ContactDtos, Person person);
}
