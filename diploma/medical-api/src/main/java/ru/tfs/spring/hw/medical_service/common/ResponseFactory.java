package ru.tfs.spring.hw.medical_service.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Slf4j
public class ResponseFactory {

    public static <T> JsonItem<T> failResult(Throwable t) {
        JsonItem<T> jsonItem = new JsonItem<>();
        jsonItem.setSuccess(false);
        jsonItem.setMessage(t.getMessage());
        jsonItem.setStacktrace(getStackTrace(t));
        jsonItem.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        log.error("Fail result. Message - " + jsonItem.getMessage() + " Stacktrace - " + jsonItem.getStacktrace());
        return jsonItem;
    }
}
