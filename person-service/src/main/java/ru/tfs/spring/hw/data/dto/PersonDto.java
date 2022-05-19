package ru.tfs.spring.hw.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDto {

    private Long personId;

    @NotEmpty
    private Set<AddressDto> addresses;

    @NotEmpty
    private Set<ContactDto> contacts;

    @NotEmpty
    private Set<IdentityDocumentDto> identityDocuments;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String patronymic;

    @NotNull
    private LocalDate birthDate;

    private boolean isDeleted;
}
