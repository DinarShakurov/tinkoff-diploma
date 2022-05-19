package ru.tfs.spring.hw.data.exception.dto;

import lombok.Data;

@Data
public class ErrorDto {

    private String errorCode;
    private String message;
    private String stacktrace;
}
