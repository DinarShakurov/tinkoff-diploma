package ru.tfs.hw.api_gateway.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class JsonItem<T> implements Serializable {

    private boolean success;
    private T data;
    private String code;
    private String message;
    private String stacktrace;
}
