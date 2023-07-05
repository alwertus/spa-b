package com.tretsoft.spa.web.controller.admin;

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
@Sql({"classpath:data/sql/admin-user.sql"})
class AdminUserControllerTest extends BaseIntegrationTest {

    @Autowired
    public AdminUserControllerTest(MockMvc mockMvc) {
        super(mockMvc, "/admin-user");
    }

    @Test
    public void getRoleList_success() throws Exception {
        mockMvc.perform(get(URL + "/roles")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("admin-user1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    public void getRoleList_forbidden() throws Exception {
        mockMvc.perform(get(URL + "/roles")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("common-user1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUserList_success() throws Exception {
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("admin-user1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    public void getUserList_forbidden() throws Exception {
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("common-user1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addRoleToUser_success() throws Exception {
        mockMvc.perform(post(URL + "/role")
                    .header(HttpHeaders.AUTHORIZATION, getTokenByUser("admin-user1"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        { "userLogin": "userForCreate",
                        "roleName": "PAGE_FEEDING"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['roles'].length()").value(5));
    }

    @Test
    public void addRoleToUser_alreadyExists_success() throws Exception {
        mockMvc.perform(post(URL + "/role")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("admin-user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        { "userLogin": "userForUpdate",
                        "roleName": "USER"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['roles'].length()").value(4));
    }

    @Test
    public void addRoleToUser_forbidden() throws Exception {
        mockMvc.perform(post(URL + "/role")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("common-user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        { "userLogin": "userForUpdate",
                        "roleName": "USER"}
                        """))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addRoleToUser_roleNotFound() throws Exception {
        mockMvc.perform(post(URL + "/role")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("admin-user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        { "userLogin": "userForUpdate",
                        "roleName": "UNKNOWN_ROLE"}
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addRoleToUser_userNotFound() throws Exception {
        mockMvc.perform(post(URL + "/role")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("admin-user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        { "userLogin": "UNKNOWN_USER",
                        "roleName": "USER"}
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void removeRoleFromUser_success() throws Exception {
        mockMvc.perform(delete(URL + "/role")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("admin-user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        { "userLogin": "userForDel",
                        "roleName": "USER"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['roles'].length()").value(3));
    }

    @Test
    public void removeRoleFromUser_forbidden() throws Exception {
        mockMvc.perform(delete(URL + "/role")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("common-user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        { "userLogin": "userForDel",
                        "roleName": "USER"}
                        """))
                .andExpect(status().isForbidden());
    }

}