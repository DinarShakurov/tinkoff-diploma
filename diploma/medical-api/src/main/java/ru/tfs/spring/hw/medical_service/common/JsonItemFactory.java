package ru.tfs.spring.hw.medical_service.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Component
@Slf4j
public class JsonItemFactory {

    @Value("${medical-service.exception.print-stack-trace:false}")
    private boolean printStackTrace;

    public <T> JsonItem<T> successResult(T data) {
        JsonItem<T> jsonItem = new JsonItem<>();
        jsonItem.setData(data);
        jsonItem.setSuccess(true);
        return jsonItem;
    }

    public <T> JsonItem<T> successResult() {
        JsonItem<T> jsonItem = new JsonItem<>();
        jsonItem.setSuccess(true);
        return jsonItem;
    }

    public <T> JsonItem<T> failResult(String code, String message, Throwable throwable) {
        JsonItem<T> jsonItem = new JsonItem<>();
        jsonItem.setSuccess(false);
        jsonItem.setCode(code);
        jsonItem.setMessage(message);
        workWithStacktrace(jsonItem, throwable);
        return jsonItem;
    }

    private <T> void workWithStacktrace(JsonItem<T> jsonItem, Throwable throwable) {
        if (printStackTrace) {
            jsonItem.setStacktrace(getStackTrace(throwable));
        } else {
            jsonItem.setStacktrace("");
            log.error(jsonItem.getMessage(), throwable);
        }
    }
}
