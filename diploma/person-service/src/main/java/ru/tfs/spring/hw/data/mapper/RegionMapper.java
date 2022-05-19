package ru.tfs.spring.hw.data.mapper;

import org.mapstruct.Mapper;
import ru.tfs.spring.hw.data.dto.RegionDto;
import ru.tfs.spring.hw.data.entity.Region;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    RegionDto map(Region region);
}
