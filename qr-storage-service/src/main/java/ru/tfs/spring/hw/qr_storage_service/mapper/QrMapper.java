package ru.tfs.spring.hw.qr_storage_service.mapper;

import org.mapstruct.Mapper;
import ru.tfs.spring.hw.qr_storage_service.dto.QrDto;
import ru.tfs.spring.hw.qr_storage_service.entity.Qr;

@Mapper(componentModel = "spring")
public interface QrMapper {

    QrDto map(Qr qr);
}
