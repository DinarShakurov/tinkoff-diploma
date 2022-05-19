package ru.tfs.spring.hw.data.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tfs.spring.hw.data.common.JsonItem;
import ru.tfs.spring.hw.data.common.JsonItemFactory;
import ru.tfs.spring.hw.data.exception.CommonException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static ru.tfs.spring.hw.data.exception.ErrorCode.VALIDATION_ERROR;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonItem handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return jsonItemFactory.failResult(VALIDATION_ERROR, getLocalizedMessage("error.validation.default"), e);
    }

    @ExceptionHandler(Exception.class)
    public JsonItem handleUnknownException(Exception e) {
        return jsonItemFactory.failResult(INTERNAL_SERVER_ERROR.getReasonPhrase(), getLocalizedMessage("error.default"), e);
    }

    private String getLocalizedMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
