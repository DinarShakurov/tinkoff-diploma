package ru.tfs.spring.hw.data.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.tfs.spring.hw.data.dto.AddressDto;
import ru.tfs.spring.hw.data.entity.Address;
import ru.tfs.spring.hw.data.entity.PersonToAddress;

import java.util.Set;

@Mapper(componentModel = "spring", uses = RegionMapper.class)
public interface AddressMapper {

    @IterableMapping(qualifiedByName = "mapPersonToAddressToAddressDto")
    Set<AddressDto> mapToAddressesDto(Set<PersonToAddress> personToAddress);

    @Mapping(target = ".", source = "address")
    @Named("mapPersonToAddressToAddressDto")
    AddressDto mapPersonToAddressToAddressDto(PersonToAddress personToAddress);

    @Mapping(target = "addressType", ignore = true)
    AddressDto mapToAddressDto(Address address);
}
