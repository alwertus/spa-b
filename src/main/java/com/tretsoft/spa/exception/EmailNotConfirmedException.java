package com.tretsoft.spa.exception;

public class EmailNotConfirmedException extends BaseException {

    public EmailNotConfirmedException(String login, Object[] params) {
        super("Mail %s not confirmed for login=" + login, params);
    }

}
