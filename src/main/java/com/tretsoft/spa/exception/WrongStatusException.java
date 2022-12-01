package com.tretsoft.spa.exception;

public class WrongStatusException extends BaseException {

    public WrongStatusException(Object[] params) {
        super("Error! Wrong status %s", params);
    }

    public WrongStatusException(String param) {
        this(new String[] {param});
    }

}
