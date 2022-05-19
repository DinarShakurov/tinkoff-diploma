package ru.tfs.spring.hw.data.dto;

import lombok.Data;
import ru.tfs.spring.hw.data.entity.AddressType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddressDto {

    @NotNull
    private AddressType addressType;
    @NotNull
    private RegionDto region;
    @NotBlank
    private String locality;
    @NotBlank
    private String houseNumber;
    private String apartmentNumber;
}
