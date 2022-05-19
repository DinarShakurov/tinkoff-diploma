package ru.tfs.spring.hw.data.services.address;

import ru.tfs.spring.hw.data.dto.AddressDto;
import ru.tfs.spring.hw.data.entity.Address;
import ru.tfs.spring.hw.data.entity.Person;
import ru.tfs.spring.hw.data.entity.PersonToAddress;

import java.util.List;
import java.util.Set;

public interface AddressService {

    Set<PersonToAddress> createForPerson(Set<AddressDto> addresses, Person person);

    Address findOrCreate(AddressDto dto);
}
