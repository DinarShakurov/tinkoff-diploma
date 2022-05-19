package ru.tfs.spring.hw.data.validator;

public interface Validator<T> {

    void validate(T t);
}
