package com.tretsoft.spa;

import io.restassured.path.json.JsonPath;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public abstract class BaseIntegrationTest {
    protected final MockMvc mockMvc;
    protected final String URL;

    public BaseIntegrationTest(MockMvc mockMvc, String URL) {
        this.mockMvc = mockMvc;
        this.URL = URL;
    }

    protected String readContentFromFile(String jsonFilename) throws IOException {
        return Files.readString(new ClassPathResource("data/json/" + jsonFilename + ".json").getFile().toPath());
    }

    // old method
    protected String getTokenByUserFromFile(String jsonFilename) throws Exception {
        MvcResult result = mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readContentFromFile(jsonFilename))
        ).andReturn();
        return JsonPath
                .from(result.getResponse().getContentAsString())
                .getJsonObject("token");
    }

    // new method. remove files
    protected String getTokenByUser(String login) throws Exception {
        MvcResult result = mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readContentFromFile("login-template").replace("#", login))
        ).andReturn();
        return JsonPath
                .from(result.getResponse().getContentAsString())
                .getJsonObject("token");
    }

}
