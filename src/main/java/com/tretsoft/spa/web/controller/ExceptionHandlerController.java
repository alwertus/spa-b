package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.exception.BaseException;
import com.tretsoft.spa.exception.SimpleException;
import com.tretsoft.spa.web.dto.ResponseError;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
public abstract class ExceptionHandlerController {

    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(BaseException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(ex));
    }

    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(new SimpleException(ex.getMessage())));
    }

    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(DataIntegrityViolationException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(new SimpleException(ex.getMessage())));
    }

}
