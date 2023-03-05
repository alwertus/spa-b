package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.BaseIntegrationTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"classpath:data/sql/user.sql", "classpath:data/sql/insert-doings.sql"})
class DoLogControllerTest extends BaseIntegrationTest {

    private final String TASK_URL = "/doings-task";

    @Autowired
    public DoLogControllerTest(MockMvc mockMvc) {
        super(mockMvc, "/doings-log");
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    /*
    update task: set startDate to null
    should create log automatically
     */
    @Test
    public void create() throws Exception {
        String token = getTokenByUser("user2");

        // make sure user2 hasn't logs
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        // find task
        MvcResult result = mockMvc.perform(get(TASK_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();
        Long id = JsonPath
                .from(result.getResponse().getContentAsString())
                .getLong("[0]['id']");
        String name = JsonPath
                .from(result.getResponse().getContentAsString())
                .getString("[0]['name']");

        // make changes: set date=null
        mockMvc.perform(put(TASK_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{ \"id\": %s, \"name\": \"%s\", \"startDate\": null }", id, name)))
                .andExpect(status().isOk());

        // check log is created
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        // make changes: set date
        mockMvc.perform(put(TASK_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{ \"id\": %s, \"name\": \"%s\", \"startDate\": %d }", id, name, Calendar.getInstance().getTimeInMillis())))
                .andExpect(status().isOk());

        // make changes: set date=null
        mockMvc.perform(put(TASK_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{ \"id\": %s, \"name\": \"%s\", \"startDate\": null }", id, name)))
                .andExpect(status().isOk());

        // check log is created
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void getReport_daily() {

    }

}