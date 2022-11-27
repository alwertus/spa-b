package com.tretsoft.spa.exception;

public class AlreadyExistsException extends BaseException {

    public AlreadyExistsException(Object[] params) {
        super("Record with %s already exist", params);
    }

}
