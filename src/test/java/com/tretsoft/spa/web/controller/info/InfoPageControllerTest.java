package com.tretsoft.spa.web.controller.info;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Sql({"classpath:data/sql/user.sql", "classpath:data/sql/insert-info.sql"})
class InfoPageControllerTest extends BaseIntegrationTest {

    @Autowired
    public InfoPageControllerTest(MockMvc mockMvc) {
        super(mockMvc, "/info-page");
    }

    @Test
    public void getPageList_success() throws Exception {
        mockMvc.perform(get(URL + "/list")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("spaceId", "1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void getPageHtml_success() throws Exception {
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("spaceId", "1000")
                        .param("pageId", "1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("html").value("TEXT TEXT TEXT 1"));
    }
}