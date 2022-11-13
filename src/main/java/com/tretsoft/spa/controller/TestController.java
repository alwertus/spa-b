package com.tretsoft.spa.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class TestController {

    @GetMapping
    public String testMethod1() {
        log.info("Input method GET");
        return "TEST METHOD - GET";
    }

    @PostMapping
    public String testMethod2() {
        log.info("Input method POST");
        return "TEST METHOD - POST";
    }

}
