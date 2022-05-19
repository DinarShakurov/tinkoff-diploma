package ru.tfs.spring.hw.data.services.address;

import org.springframework.stereotype.Service;
import ru.tfs.spring.hw.data.dto.AddressDto;
import ru.tfs.spring.hw.data.entity.AddressType;

import java.util.Collection;

import static ru.tfs.spring.hw.data.exception.CommonException.buildException;
import static ru.tfs.spring.hw.data.exception.ErrorCode.VALIDATION_ERROR;

@Service
public class AddressValidatorService {

    public void validate(AddressDto addressDto) {
        // типа валидируем
    }

    public void onlyOneRegistrationAddress(Collection<AddressDto> addresses) {
        long count = addresses.stream()
                .filter(addressDto -> AddressType.REGISTRATION.equals(addressDto.getAddressType()))
                .count();
        if (count != 1) {
            throw buildException("error.validation.address.onlyOneRegistration", VALIDATION_ERROR, count);
        }
    }
}
