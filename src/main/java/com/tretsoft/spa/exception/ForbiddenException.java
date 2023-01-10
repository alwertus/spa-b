package com.tretsoft.spa.exception;

public class ForbiddenException extends BaseException {

    public ForbiddenException(Object[] params) {
        super("Error! User does not have access to %s", params);
    }

    public ForbiddenException(String param) {
        this(new String[] {param});
    }

}
