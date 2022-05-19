package ru.tfs.spring.hw.data.services.region;

import ru.tfs.spring.hw.data.entity.Region;

import java.util.Collection;

public interface RegionService {

    Collection<Region> getAllRegions();

    Region getRegion(Integer regionId);
}
