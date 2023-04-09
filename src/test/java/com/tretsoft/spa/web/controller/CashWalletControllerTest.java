package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.BaseIntegrationTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Sql({"classpath:data/sql/user.sql"})
class CashWalletControllerTest extends BaseIntegrationTest {

    @Autowired
    public CashWalletControllerTest(MockMvc mockMvc) {
        super(mockMvc, "/cash-wallet");
    }

    @Test
    void createWalletWithDefaultHidden() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"USD\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("USD"))
                .andExpect(jsonPath("hidden").value(false))
        ;
    }

    @Test
    void createWalletWithCustomHidden() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"RUB\", \"hidden\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("RUB"))
                .andExpect(jsonPath("hidden").value(true))
        ;
    }

}