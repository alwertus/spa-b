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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Sql({"classpath:data/sql/user.sql", "classpath:data/sql/insert-doings.sql"})
public class DoLabelControllerTest extends BaseIntegrationTest {

    @Autowired
    public DoLabelControllerTest(MockMvc mockMvc) {
        super(mockMvc, "/doings-label");
    }

    @Test
    void getLabelList() throws Exception {
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUserFromFile("user1-login"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void createLabel() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUserFromFile("userCreate-login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"lbl11\", \"color\": \"#00FF00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("lbl11"))
                .andExpect(jsonPath("color").value("#00FF00"));
    }

    @Test
    void createDuplicate_shouldReturnException() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUserFromFile("user1-login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Useful\", \"color\": \"#00FF00\"}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updateLabel() throws Exception {
        mockMvc.perform(put(URL)
                .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": 1000, \"name\": \"NewValue\", \"color\": \"#FFFFFF\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("NewValue"))
                .andExpect(jsonPath("color").value("#FFFFFF"));
    }

}
