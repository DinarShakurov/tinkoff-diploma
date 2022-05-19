package ru.tfs.spring.hw.data.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrettyPersonDto {

    private Long personId;
    private String fio;
    private LocalDate birthDate;
    private IdentityDocumentDto document;
    private AddressDto registrationAddress;
    private ContactDto phone;
}
