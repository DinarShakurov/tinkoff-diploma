package ru.tfs.spring.hw.medical_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tfs.spring.hw.medical_service.dto.VaccinationDto;
import ru.tfs.spring.hw.medical_service.dto.VaccinationKafkaDto;
import ru.tfs.spring.hw.medical_service.entity.Vaccination;

@Mapper(componentModel = "spring")
public interface VaccinationMapper {

    @Mapping(target = "vaccineName", source = "vaccine.vaccineName")
    @Mapping(target = "vaccinationPointNumber", source = "vaccinationPoint.vaccinationPointNumber")
    @Mapping(target = "vaccinationPointName", source = "vaccinationPoint.vaccinationPointName")
    @Mapping(target = "city", source = "vaccinationPoint.city")
    @Mapping(target = "address", source = "vaccinationPoint.address")
    VaccinationDto map(Vaccination vaccination);

    @Mapping(target = "vaccineName", source = "vaccine.vaccineName")
    VaccinationKafkaDto mapToKafka(Vaccination vaccination);
}
