package ru.tfs.spring.hw.qr_storage_service.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tfs.spring.hw.qr_storage_service.common.JsonItem;
import ru.tfs.spring.hw.qr_storage_service.common.JsonItemFactory;
import ru.tfs.spring.hw.qr_storage_service.exception.CommonException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerAdviceExceptionHandler {

    private final JsonItemFactory jsonItemFactory;
    private final MessageSource messageSource;

    @ExceptionHandler(CommonException.class)
    public JsonItem handleCommonException(CommonException e) {
        return jsonItemFactory.failResult(e.getErrorCode(), getLocalizedMessage(e.getMessage(), e.getMessageArguments()), e);
    }

    @ExceptionHandler(Exception.class)
    public JsonItem handleUnknownException(Exception e) {
        return jsonItemFactory.failResult(INTERNAL_SERVER_ERROR.getReasonPhrase(), getLocalizedMessage("error.default"), e);
    }

    private String getLocalizedMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
