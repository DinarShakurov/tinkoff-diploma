package ru.tfs.spring.hw.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tfs.spring.hw.data.dto.ContactDto;
import ru.tfs.spring.hw.data.entity.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(target = "personId", source = "person.personId")
    ContactDto map(Contact contact);
}
