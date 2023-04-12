package com.tretsoft.spa.web.controller.cash;

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
@Sql({"classpath:data/sql/user.sql","classpath:data/sql/insert-wallet.sql"})
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
                        .content("{ \"name\": \"newUSD\", \"currency\": { \"name\": \"USD\"} }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("newUSD"))
                .andExpect(jsonPath("currency.name").value("USD"))
                .andExpect(jsonPath("hidden").value(false));
    }

    @Test
    void createWalletWithCustomHidden() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"newRUB\", \"hidden\": true, \"currency\": { \"name\": \"RUB\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("newRUB"))
                .andExpect(jsonPath("currency.name").value("RUB"))
                .andExpect(jsonPath("hidden").value(true));
    }

    @Test
    void createWallet_thanGetWithWrongUser_noAccess() throws Exception {
        MvcResult result = mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"test_access\", \"hidden\": true, \"currency\": { \"name\": \"RUB\"}}"))
                .andExpect(status().isOk())
                .andReturn();
        long id = JsonPath
                .from(result.getResponse().getContentAsString())
                .getLong("id");

        mockMvc.perform(get(URL + "/" + id)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserWallets() throws Exception {
        mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user2")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void updateWalletSuccess() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1000, \"name\": \"WALLET111\", \"hidden\": true, \"currency\": { \"name\": \"KGS\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("WALLET111"))
                .andExpect(jsonPath("currency.name").value("KGS"))
                .andExpect(jsonPath("hidden").value(true));
    }

    @Test
    void updateWalletForbidden() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user2"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1000, \"name\": \"WALLET111\", \"hidden\": true, \"currency\": { \"name\": \"KGS\"}}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteSuccess() throws Exception {
        mockMvc.perform(delete(URL + "/1002")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForDel")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteForbidden() throws Exception {
        mockMvc.perform(delete(URL + "/1003")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getValletCells() throws Exception {
        mockMvc.perform(get(URL + "/1000")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['cells']").exists())
                .andExpect(jsonPath("$['cells'].length()").value(3));
    }

}