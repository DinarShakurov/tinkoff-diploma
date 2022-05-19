package ru.tfs.spring.hw.data.validator.document;

import org.springframework.stereotype.Component;
import ru.tfs.spring.hw.data.dto.IdentityDocumentDto;
import ru.tfs.spring.hw.data.entity.DocumentType;

@Component
public class InternationalPassportIdentityDocumentValidator extends AbstractIdentityDocumentValidator {

    @Override
    public void validate(IdentityDocumentDto dto) {
        super.validate(dto);
    }

    @Override
    public DocumentType getDocumentType() {
        return DocumentType.INTERNATIONAL_PASSPORT;
    }
}
