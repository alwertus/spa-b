package com.tretsoft.spa.exception;

public class NullAttributeException extends BaseException {

    public NullAttributeException(Object[] params) {
        super("Empty attribute: %s", params);
    }
}
