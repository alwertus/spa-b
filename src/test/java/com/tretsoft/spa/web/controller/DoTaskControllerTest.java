package com.tretsoft.spa.web.controller;

import com.tretsoft.spa.BaseIntegrationTest;
import io.restassured.path.json.JsonPath;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Sql({"classpath:data/sql/user.sql", "classpath:data/sql/insert-doings.sql"})
class DoTaskControllerTest extends BaseIntegrationTest {

    @Autowired
    public DoTaskControllerTest(MockMvc mockMvc) {
        super(mockMvc, "/doings-task");
    }

    @Test
    void getAllTasks() throws Exception {
        // 2 records for user1
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUserFromFile("user1-login"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]['labels'].length()").value(1))
                .andExpect(jsonPath("$[1]['labels'].length()").value(1));

        // 1 records for user2
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUserFromFile("user2-login"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]['labels'].length()").value(0));
    }

    @Test
    void create_shouldReturnSuccess() throws Exception {
        // login
        String token = getTokenByUserFromFile("userCreate-login");

        // create
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"NewTask1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("NewTask1"))
                .andExpect(jsonPath("checked").value(true));
    }

    @Test
    void createUnchecked_shouldReturnSuccess() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUserFromFile("userCreate-login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"NewTask2\", \"checked\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("NewTask2"))
                .andExpect(jsonPath("checked").value(false));
    }

    @Test
    void createDuplicate_returnError() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUserFromFile("user1-login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Work\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_success() throws Exception {
        // login
        String token = getTokenByUserFromFile("userDelete-login");

        // get id
        MvcResult result = mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        long id = JsonPath
                .from(result.getResponse().getContentAsString())
                .getLong("[0].id");

        // delete
        mockMvc.perform(delete(URL + "/" + id)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());

        // check
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void delete_userHasNotAccess_error() throws Exception {
        // login
        String tokenOtherUser = getTokenByUserFromFile("user1-login");
        String tokenCurrentUser = getTokenByUserFromFile("userDelete-login");

        // get id
        MvcResult result = mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, tokenOtherUser)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        long id = JsonPath
                .from(result.getResponse().getContentAsString())
                .getLong("[0].id");

        // delete
        mockMvc.perform(delete(URL + "/" + id)
                        .header(HttpHeaders.AUTHORIZATION, tokenCurrentUser))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_withWrongUser_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForDel"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1000, \"name\": \"taskForUpdate111\" }"))
                .andExpect(status().isBadRequest());
    }

    /* Add label variants:
        1. add exists label by id
        2. add new label by name (autocreate)
     */
    @Test
    void update_addLabel_existsById_than_remove_shouldReturnOK() throws Exception {
        // add label
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1001, \"name\": \"taskForAddLabel\", \"labels\": [{\"id\":  1001}] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("taskForAddLabel"))
                .andExpect(jsonPath("$['labels'].length()").value(1))
                .andExpect(jsonPath("$['labels'][0]['id']").value(1001))
                .andExpect(jsonPath("$['labels'][0]['name']").value("LabelToAdd"));

        // clear label list
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1001, \"name\": \"taskForAddLabel\", \"labels\": [] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("taskForAddLabel"))
                .andExpect(jsonPath("$['labels'].length()").value(0));
    }

    @Test
    void update_addLabelByName_than_remove_shouldCreateNewLabelAndAddToTask() throws Exception {
        // add label
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1001, \"name\": \"taskForAddLabel\", \"labels\": [{\"name\":  \"KEKE1\"}] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("taskForAddLabel"))
                .andExpect(jsonPath("$['labels'].length()").value(1))
                .andExpect(jsonPath("$['labels'][0]['name']").value("KEKE1"));

        // clear label list
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1001, \"name\": \"taskForAddLabel\", \"labels\": [] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("taskForAddLabel"))
                .andExpect(jsonPath("$['labels'].length()").value(0));
    }

    @Test
    void createTaskWithLabels() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"NewTask3\", \"checked\": false, \"labels\": [{\"name\":  \"KEKE2\"}, {\"id\":  1001}] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("NewTask3"))
                .andExpect(jsonPath("checked").value(false))
                .andExpect(jsonPath("$['labels'].length()").value(2));
    }

}