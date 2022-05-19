package ru.tfs.spring.hw.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegionDto {
    @NotNull
    private Integer regionId;
    private String regionCode;
    private String regionName;
    private String kladrId;
    private String fiasId;
}
