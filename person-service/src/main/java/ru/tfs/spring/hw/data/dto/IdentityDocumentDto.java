package ru.tfs.spring.hw.data.dto;

import lombok.Data;
import ru.tfs.spring.hw.data.entity.DocumentType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class IdentityDocumentDto {

    private Long documentId;

    @NotNull
    private DocumentType documentType;

    @NotBlank
    private String number;

    private boolean primary;

    private Long personId;
}
