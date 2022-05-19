package ru.tfs.spring.hw.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tfs.spring.hw.data.entity.Region;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Integer> {

    Optional<Region> findByRegionName(String regionName);
}
