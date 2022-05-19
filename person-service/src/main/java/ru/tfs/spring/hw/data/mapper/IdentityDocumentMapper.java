package ru.tfs.spring.hw.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tfs.spring.hw.data.dto.IdentityDocumentDto;
import ru.tfs.spring.hw.data.entity.IdentityDocument;

@Mapper(componentModel = "spring")
public interface IdentityDocumentMapper {

    @Mapping(target = "personId", source = "person.personId")
    IdentityDocumentDto map(IdentityDocument identityDocument);
}
