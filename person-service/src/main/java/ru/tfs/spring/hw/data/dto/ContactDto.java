package ru.tfs.spring.hw.data.dto;

import lombok.Data;
import ru.tfs.spring.hw.data.entity.ContactType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ContactDto {

    private Long contactId;

    @NotNull
    private ContactType contactType;

    @NotBlank
    private String value;

    private Long personId;
}
