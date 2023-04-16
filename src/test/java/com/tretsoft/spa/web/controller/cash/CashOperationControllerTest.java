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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Sql({"classpath:data/sql/user.sql","classpath:data/sql/insert-wallet.sql"})
class CashOperationControllerTest extends BaseIntegrationTest {

    @Autowired
    public CashOperationControllerTest(MockMvc mockMvc, CashOperationController controller) {
        super(mockMvc, controller.getUrl());
    }

    @Test
    public void create_attributesFull_success() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellSource": { "id": 1006 },
                                "walletCellDestination": { "id": 1007 },
                                "product": { "id": 1005 },
                                "sum": 1000.50,
                                "rate": 1.5,
                                "transferFee": 100.50,
                                "notes": "note note" }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("walletCellSource.id").value(1006))
                .andExpect(jsonPath("walletCellDestination.id").value(1007))
                .andExpect(jsonPath("product.id").value(1005))
                .andExpect(jsonPath("sum").value(1000.50))
                .andExpect(jsonPath("rate").value(1.5))
                .andExpect(jsonPath("transferFee").value(100.50))
                .andExpect(jsonPath("notes").value("note note"));
    }

    @Test
    public void create_withCompositeSum_success() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellSource": { "id": 1006 }, "compositeSum": "900+50+10+40+0.01" }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("sum").value(1000.01));
    }

    @Test
    public void create_noWallets_error() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "sum": 100 }"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_wrongWalletCellSource_forbidden() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellDestination": { "id": 1003 }, "sum": 100 }"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_wrongProduct_forbidden() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellDestination": { "id": 1006 }, "product": { "id": 1001 }, "sum": 100 }"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_wrongWalletCellDestination_forbidden() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellSource": { "id": 1003 }, "sum": 100 }"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_noSumOrCompositeSum_error() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellDestination": { "id": 1006 }, "product": { "id": 1001 } }"""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_minimumAttributes_checkAutofill_success() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellSource": { "id": 1006 }, "sum": 100 }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("walletCellSource.id").value(1006))
                .andExpect(jsonPath("sum").value(100))
                .andExpect(jsonPath("rate").value(1))
                .andExpect(jsonPath("transferFee").value(0))
                .andExpect(jsonPath("isAutofill").value(false));
    }


/*

    @Test
    public void read() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }
*/

}