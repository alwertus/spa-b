package com.tretsoft.spa.web.controller.cash;

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
@Sql({"classpath:data/sql/user.sql","classpath:data/sql/insert-wallet.sql"})
class CashWalletCellControllerTest extends BaseIntegrationTest {

    @Autowired
    public CashWalletCellControllerTest(MockMvc mockMvc) {
        super(mockMvc, "/cash-wallet-cell");
    }

    @Test
    public void getById_success() throws Exception {
        mockMvc.perform(get(URL + "/1000")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("icon").value("icon1"))
                .andExpect(jsonPath("name").value("cell1"))
                .andExpect(jsonPath("walletId").value(1000));
    }

    @Test
    public void getById_forbidden() throws Exception {
        mockMvc.perform(get(URL + "/1000")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user2")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_success() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"walletId\": 1004, \"name\": \"test_add\", \"hidden\": true, \"icon\": \"test_icon_1\", \"notes\": \"kek kek\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("icon").value("test_icon_1"))
                .andExpect(jsonPath("name").value("test_add"))
                .andExpect(jsonPath("notes").value("kek kek"))
                .andExpect(jsonPath("hidden").value(true))
                .andExpect(jsonPath("walletId").value(1004));

        // check
        mockMvc.perform(get("/cash-wallet")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]['cells'].length()").value(1))
                .andExpect(jsonPath("[0]['cells'][0].name").value("test_add"))
                .andExpect(jsonPath("[0]['cells'][0].icon").value("test_icon_1"))
                .andExpect(jsonPath("[0]['cells'][0].notes").value("kek kek"))
                .andExpect(jsonPath("[0]['cells'][0].hidden").value(true));
    }

    @Test
    public void create_forbidden() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"walletId\": 1004, \"name\": \"test_add\", \"hidden\": true, \"icon\": \"test_icon_1\", \"notes\": \"kek kek\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_success() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1003, \"walletId\": 1005, \"name\": \"new_name\", \"hidden\": true, \"icon\": \"new_icon\", \"notes\": \"new_note\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("icon").value("new_icon"))
                .andExpect(jsonPath("name").value("new_name"))
                .andExpect(jsonPath("notes").value("new_note"))
                .andExpect(jsonPath("hidden").value(true))
                .andExpect(jsonPath("walletId").value(1005));
    }

    @Test
    public void update_forbidden() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1003, \"walletId\": 1005, \"name\": \"new_name\", \"hidden\": true, \"icon\": \"new_icon\", \"notes\": \"new_note\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_success() throws Exception {
        mockMvc.perform(delete(URL + "/1004")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForDel")))
                .andExpect(status().isOk());
    }

    @Test
    void delete_forbidden() throws Exception {
        mockMvc.perform(delete(URL + "/1005")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isBadRequest());
    }

}