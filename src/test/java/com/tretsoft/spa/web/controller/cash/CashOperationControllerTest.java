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

    //    TODO: REPEAR
//    @Test
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
    public void create_withNewProduct_successAndCreateProduct() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellSource": { "id": 1006 },
                                "product": {"name":  "newProduct"},
                                "sum": 50 }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("sum").value(50));
    }

    @Test
    public void create_withExistsProductByName_ignoreCamel_success() throws Exception {
        mockMvc.perform(post(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForCreate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "walletCellSource": { "id": 1006 },
                                "product": {"name":  "pRoducT-operAtioN 1"},
                                "sum": 50 }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("sum").value(50))
                .andExpect(jsonPath("product.id").value(1005))
                .andExpect(jsonPath("product.name").value("product-operation 1"))
        ;
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

    //    TODO: REPEAR
//    @Test
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

    @Test
    public void update_productById_success() throws Exception {
        mockMvc.perform(put(URL)
                .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(""" 
                    { "id": 1000,
                    "walletCellDestination": { "id": 1003 },
                    "product": { "id": 1006 },
                    "sum": 100,
                    "rate": 1,
                    "transferFee": 0
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("product.name").value("update product 2"));
    }

    @Test
    public void update_productByName_success() throws Exception {
        mockMvc.perform(put(URL)
                .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(""" 
                    { "id": 1000,
                    "walletCellDestination": { "id": 1003 },
                    "product": { "name":  "update product 3"},
                    "sum": 100,
                    "rate": 1,
                    "transferFee": 0
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("product.id").value(1007));
    }

    @Test
    public void update_productByName_successAndProductCreated() throws Exception {
        mockMvc.perform(put(URL)
                .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(""" 
                    { "id": 1000,
                    "walletCellDestination": { "id": 1003 },
                    "product": { "name":  "product1"},
                    "sum": 100,
                    "rate": 1,
                    "transferFee": 0
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("product.name").value("product1"));
    }

    @Test
    public void update_sumRateFee_success() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                    { "id": 1000,
                    "walletCellDestination": { "id": 1003 },
                    "product": { "id":  1002},
                    "sum": 111.1,
                    "rate": 1.5,
                    "transferFee": 50.8
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("sum").value(111.1))
                .andExpect(jsonPath("rate").value(1.5))
                .andExpect(jsonPath("transferFee").value(50.8))
                .andExpect(jsonPath("compositeSum").doesNotExist());
    }

    @Test
    public void update_compositeSum_success() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                            { "id": 1000,
                            "walletCellDestination": { "id": 1003 },
                            "product": { "id":  1002},
                            "rate": 1,
                            "transferFee": 0,
                            "compositeSum": "-100+200-50.5+15"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("sum").value(64.5))
                .andExpect(jsonPath("rate").value(1))
                .andExpect(jsonPath("transferFee").value(0))
                .andExpect(jsonPath("compositeSum").value("-100+200-50.5+15"));
    }

    @Test
    public void update_walletCellsIsEmpty_badRequest() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "id": 1000,
                            "product": { "id":  1002},
                            "sum": 100,
                            "rate": 1,
                            "transferFee": 0
                            }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_walletCellDestination_forbidden() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "id": 1000,
                            "walletCellDestination": { "id": 1000 },
                            "product": { "id":  1002},
                            "sum": 100,
                            "rate": 1,
                            "transferFee": 0
                            }
                        """))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void update_walletCellSource_forbidden() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "id": 1000,
                            "walletCellSource": { "id": 1000 },
                            "product": { "id":  1002},
                            "sum": 100,
                            "rate": 1,
                            "transferFee": 0
                            }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_walletCells_success() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "id": 1000,
                            "walletCellDestination": { "id": 1008 },
                            "walletCellSource": { "id": 1003 },
                            "product": { "id":  1002},
                            "sum": 100,
                            "rate": 1,
                            "transferFee": 0
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("walletCellDestination.name").value("cellUpdate2"))
                .andExpect(jsonPath("walletCellSource.name").value("cell4"));
    }

    @Test
    public void update_walletCellsIsEquals_badRequest() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "id": 1000,
                            "walletCellDestination": { "id": 1008 },
                            "walletCellSource": { "id": 1008 },
                            "product": { "id":  1002},
                            "sum": 100,
                            "rate": 1,
                            "transferFee": 0
                            }
                        """))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void update_notes_success() throws Exception {
        mockMvc.perform(put(URL)
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "id": 1000,
                            "walletCellDestination": { "id": 1008 },
                            "product": { "id":  1002},
                            "sum": 100,
                            "rate": 1,
                            "transferFee": 0,
                            "notes": "NOTE 111"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("notes").value("NOTE 111"));
    }

    @Test
    void delete_success() throws Exception {
        String token = getTokenByUser("userForDel");

        mockMvc.perform(delete(URL + "/1001")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());

        //check
        mockMvc.perform(get(URL + "/1001")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_forbidden() throws Exception {
        mockMvc.perform(delete(URL + "/1002")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForUpdate")))
                .andExpect(status().isBadRequest());
    }
/*

    @Test
    public void read() throws Exception {

    }
*/

}