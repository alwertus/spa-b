package com.tretsoft.spa.exception;

public class BadRequestException extends BaseException {

    public BadRequestException(Object[] params) {
        super("Wrong attribute: %s", params);
    }
}
