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
class CashProductControllerTest extends BaseIntegrationTest {

    private final String cellControllerUrl;

    @Autowired
    public CashProductControllerTest(MockMvc mockMvc, CashProductController controller, CashWalletCellController cashWalletCellController) {
        super(mockMvc, controller.getUrl());
        cellControllerUrl = cashWalletCellController.getUrl();
    }

    @Test
    public void create_minAttributes_success() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"product 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("product 1"));
    }

    @Test
    public void create_fullAttributes_success() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"product 2\", \"grade\": 3 , \"defaultCashWalletCellSource\": {\"id\":  1006}, \"defaultCashWalletCellDestination\": {\"id\":  1007} }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("product 2"))
                .andExpect(jsonPath("grade").value(3))
                .andExpect(jsonPath("defaultCashWalletCellSource.id").value(1006))
                .andExpect(jsonPath("defaultCashWalletCellDestination.id").value(1007));

        // check cell has no changes
        mockMvc.perform(get(cellControllerUrl + "/1006")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("icon").value("i1"))
                .andExpect(jsonPath("name").value("cell-usd-1"))
                .andExpect(jsonPath("hidden").value(false))
                .andExpect(jsonPath("walletId").value(1006));
    }

    @Test
    public void create_noAttributes_exception() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllProductsByUser() throws Exception {
        mockMvc.perform(get(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void getById_success() throws Exception {
        mockMvc.perform(get(URL + "/1000")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("product1"))
                .andExpect(jsonPath("grade").value(5))
                .andExpect(jsonPath("defaultCashWalletCellSource").doesNotExist())
                .andExpect(jsonPath("defaultCashWalletCellDestination.id").value(1001));
    }

    @Test
    public void getById_forbidden() throws Exception {
        mockMvc.perform(get(URL + "/1000")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user2")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_success() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1002, \"name\": \"PPP\", \"grade\": 4 , \"defaultCashWalletCellSource\": {\"id\":  1003}, \"defaultCashWalletCellDestination\": {\"id\":  1003} }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("PPP"))
                .andExpect(jsonPath("grade").value(4))
                .andExpect(jsonPath("defaultCashWalletCellSource.id").value(1003))
                .andExpect(jsonPath("defaultCashWalletCellDestination.id").value(1003));
    }

    @Test
    public void update_wrongWalletCell_forbidden() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1002, \"name\": \"PPP\", \"grade\": 4 , \"defaultCashWalletCellSource\": {\"id\":  1001}, \"defaultCashWalletCellDestination\": {\"id\":  1001} }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_wrongUser_forbidden() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1002, \"name\": \"PPP\", \"grade\": 4 , \"defaultCashWalletCellSource\": {\"id\":  1003}, \"defaultCashWalletCellDestination\": {\"id\":  1003} }"))
                .andExpect(status().isBadRequest());
    }

//    TODO: REPEAR
//    @Test
    void delete_success() throws Exception {
        mockMvc.perform(delete(URL + "/1003")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForDel")))
                .andExpect(status().isOk());
    }

    @Test
    void delete_forbidden() throws Exception {
        mockMvc.perform(delete(URL + "/1004")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("user1")))
                .andExpect(status().isBadRequest());
    }

}