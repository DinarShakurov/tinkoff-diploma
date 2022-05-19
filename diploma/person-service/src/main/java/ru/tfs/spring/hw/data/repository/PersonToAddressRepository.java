package ru.tfs.spring.hw.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tfs.spring.hw.data.entity.PersonToAddress;

import java.util.Set;

public interface PersonToAddressRepository extends JpaRepository<PersonToAddress, Long> {

    @Query("select pta.person.personId from PersonToAddress pta where pta.address.region.regionName=:regionName")
    Set<Long> personIdsByRegionName(@Param("regionName") String regionName);
}
