package ru.tfs.spring.hw.medical_service.exception;

import lombok.Getter;

import static ru.tfs.spring.hw.medical_service.exception.ErrorCode.COMMON_ERROR;


@Getter
public class CommonException extends RuntimeException {

    private final String errorCode;
    private final Object[] messageArguments;

    public CommonException(String message, Object... messageArguments) {
        this(message, COMMON_ERROR, messageArguments);
    }

    public CommonException(String message, String code, Object... messageArguments) {
        super(message);
        this.errorCode = code;
        this.messageArguments = messageArguments;
    }

    public CommonException(String message, Throwable throwable) {
        this(message, throwable, COMMON_ERROR);
    }

    public CommonException(String message, Throwable throwable, String errorCode, Object... messageArguments) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.messageArguments = messageArguments;
    }

    public static CommonException buildException(String message, String code, Object... messageArguments) {
        return new CommonException(message, code, messageArguments);
    }
}
