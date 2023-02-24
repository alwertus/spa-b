package com.tretsoft.spa.web.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql("/data/sql/user.sql")
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void getAll() {
/*
        JsonPath json = given()
                .port(port)
                .when()
                .get("/user")
                .then()
                .statusCode(OK.value())
                .extract()
                .body()
                .jsonPath();
        System.out.println(json.prettify());*/
    }



}