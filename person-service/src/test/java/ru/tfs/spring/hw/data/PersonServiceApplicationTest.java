package ru.tfs.spring.hw.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.data.entity.Address;
import ru.tfs.spring.hw.data.entity.Region;
import ru.tfs.spring.hw.data.repository.AddressRepository;
import ru.tfs.spring.hw.data.repository.RegionRepository;

@SpringBootTest
@ActiveProfiles("test")
public class PersonServiceApplicationTest {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RegionRepository regionRepository;


    @Test
    @Transactional
    void contextLoads() {
        for (int i = 0; i < 16; i++) {
            String bla= String.valueOf(i + 100);

            Region r = new Region();
            r.setRegionCode(bla);
            r.setFiasId(bla);
            r.setKladrId(bla);
            r.setRegionName(bla);
            r = regionRepository.save(r);
            System.out.println(regionRepository.getById(r.getRegionId()));

            Address address = new Address();
            address.setRegion(r);
            address.setApartmentNumber(String.valueOf(i));
            address.setHouseNumber(String.valueOf(i));
            address.setLocality(String.valueOf(i));
            address = addressRepository.save(address);
        }
        regionRepository.flush();
        addressRepository.flush();
    }



}
