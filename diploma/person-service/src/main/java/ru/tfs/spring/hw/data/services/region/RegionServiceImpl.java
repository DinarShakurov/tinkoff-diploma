package ru.tfs.spring.hw.data.services.region;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tfs.spring.hw.data.exception.ErrorCode;
import ru.tfs.spring.hw.data.entity.Region;
import ru.tfs.spring.hw.data.repository.RegionRepository;
import ru.tfs.spring.hw.data.services.region.RegionService;

import java.util.Collection;

import static java.lang.String.format;
import static ru.tfs.spring.hw.data.exception.CommonException.buildException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Region getRegion(Integer regionId) {
        return regionRepository.findById(regionId)
                .orElseThrow(() -> buildException("error.validation.region.notFound", ErrorCode.ENTITY_NOT_FOUND));
    }
}
