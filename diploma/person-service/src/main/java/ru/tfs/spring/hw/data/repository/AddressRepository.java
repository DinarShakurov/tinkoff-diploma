package ru.tfs.spring.hw.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.hw.data.entity.Address;
import ru.tfs.spring.hw.data.entity.Region;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByRegionAndLocalityAndHouseNumberAndApartmentNumber(Region region,
                                                                              String locality,
                                                                              String houseNumber,
                                                                              String apartmentNumber);
}
