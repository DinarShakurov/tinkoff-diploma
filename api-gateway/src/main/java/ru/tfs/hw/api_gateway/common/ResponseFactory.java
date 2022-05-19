package ru.tfs.hw.api_gateway.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class ResponseFactory {

    public static <T> JsonItem<T> failResult(Throwable t) {
        JsonItem<T> jsonItem = new JsonItem<>();
        jsonItem.setSuccess(false);
        jsonItem.setMessage(t.getMessage());
        //jsonItem.setStacktrace(Arrays.toString(t.getStackTrace()));
        jsonItem.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        return jsonItem;
    }
}
