package ru.tfs.spring.hw.medical_service.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.tfs.spring.hw.medical_service.exception.dto.ErrorDto;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class JsonItem<T> implements Serializable {

    private boolean success;
    private T data;
    private List<ErrorDto> errors;
    private String code;
    private String message;
    private String stacktrace;
}
