package ru.tfs.spring.hw.medical_service.exception.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class ErrorDto {

    private String errorCode;
    private String message;
    private String stacktrace;
}
