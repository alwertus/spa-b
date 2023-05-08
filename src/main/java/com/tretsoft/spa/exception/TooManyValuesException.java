package com.tretsoft.spa.exception;

public class TooManyValuesException extends BaseException {
    public TooManyValuesException(String message) {
        super("[%s] has too many values", new String[]{message});
    }
}
