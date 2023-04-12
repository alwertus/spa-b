package com.tretsoft.spa.web.controller.doings;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"classpath:data/sql/user.sql", "classpath:data/sql/insert-doings.sql"})
class DoLogControllerTest extends BaseIntegrationTest {

    private final SimpleDateFormat sdf;
    {
        sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

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
        String TASK_URL = "/doings-task";

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


    private void getReportForPeriod_ExpectRecordCount(String sStart, String sEnd, int expectRecords) throws Exception {

        mockMvc.perform(get(URL + "/period")
                        .header(HttpHeaders.AUTHORIZATION, getTokenByUser("userForReport"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", String.valueOf(sdf.parse(sStart).getTime()))
                        .param("end", String.valueOf(sdf.parse(sEnd).getTime()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectRecords));
    }
    @Test
    public void getReportByMonth() throws Exception {
        getReportForPeriod_ExpectRecordCount("2023-01-01 00:00:00", "2023-02-01 00:00:00", 30);
    }
    @Test
    public void getReportByDay() throws Exception {
        getReportForPeriod_ExpectRecordCount("2023-01-01 00:00:00", "2023-01-02 00:00:00", 6);
    }

}