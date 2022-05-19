package ru.tfs.spring.hw.data.validator.contact;

import org.springframework.stereotype.Component;
import ru.tfs.spring.hw.data.dto.ContactDto;
import ru.tfs.spring.hw.data.entity.ContactType;

@Component
public class EmailContactValidator extends AbstractContactValidator{
    @Override
    public void validate(ContactDto dto) {
        super.validate(dto);
    }

    @Override
    public ContactType getContactType() {
        return ContactType.EMAIL;
    }
}
