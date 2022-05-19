package ru.tfs.spring.hw.data.services.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.data.dto.AddressDto;
import ru.tfs.spring.hw.data.entity.Address;
import ru.tfs.spring.hw.data.entity.Person;
import ru.tfs.spring.hw.data.entity.PersonToAddress;
import ru.tfs.spring.hw.data.entity.Region;
import ru.tfs.spring.hw.data.repository.AddressRepository;
import ru.tfs.spring.hw.data.services.region.RegionService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final RegionService regionService;
    private final AddressValidatorService validator;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public Set<PersonToAddress> createForPerson(Set<AddressDto> addresses, Person person) {
        validator.onlyOneRegistrationAddress(addresses);
        return addresses.stream()
                // Находим существующий Address или создаём новую
                .map(dto -> Pair.of(findOrCreate(dto), dto.getAddressType()))
                // Если мы нашли существующий Address, вытаскиваем из него PtA (с текущим person), иначе создаём PtA
                .map(pair -> {
                    Address address = pair.getFirst();
                    PersonToAddress personToAddress = address.getPersonToAddress().stream()
                            .filter(pta -> pta.getPerson().equals(person))
                            .findFirst()
                            .orElseGet(() -> {
                                PersonToAddress pta = new PersonToAddress();
                                pta.setPerson(person);
                                pta.setAddress(address);

                                address.getPersonToAddress().add(pta);
                                return pta;
                            });
                    personToAddress.setAddressType(pair.getSecond());
                    return personToAddress;
                }).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Address findOrCreate(AddressDto dto) {
        validator.validate(dto);
        Region region = regionService.getRegion(dto.getRegion().getRegionId());

        Address address = addressRepository.findByRegionAndLocalityAndHouseNumberAndApartmentNumber
                        (region, dto.getLocality(), dto.getHouseNumber(), dto.getHouseNumber())
                .orElseGet(() -> {
                    Address addr = new Address();
                    addr.setRegion(region);
                    addr.setLocality(dto.getLocality());
                    addr.setHouseNumber(dto.getHouseNumber());
                    addr.setApartmentNumber(dto.getApartmentNumber());
                    return addr;
                });

        log.debug("Saving or updating new address - {}", address);
        return address;
    }
}
