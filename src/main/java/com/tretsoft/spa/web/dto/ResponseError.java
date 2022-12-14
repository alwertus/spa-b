package com.tretsoft.spa.web.dto;


import com.tretsoft.spa.exception.BaseException;
import lombok.Getter;

public class ResponseError {

    @Getter
    private final String description;

    public ResponseError(BaseException ex) {
        this.description = ex.getMessage();
    }

}
