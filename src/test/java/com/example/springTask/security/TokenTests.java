package com.example.springTask.security;


import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenTests {

    private String CLIENT_ID = "client";
    private String CLIENT_SECRET = "secret";

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldGetAccessToken() throws Exception {
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("grant_type", "password");
        linkedMultiValueMap.add("username", "student");
        linkedMultiValueMap.add("password", "password");

        mockMvc.perform(post("/oauth/token")
            .params(linkedMultiValueMap)
            .with(SecurityMockMvcRequestPostProcessors.httpBasic(CLIENT_ID, CLIENT_SECRET))
            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.refresh_token").exists());
    }

    @Test
    public void shouldNotGetAccessToken() throws Exception {
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("grant_type", "password");
        linkedMultiValueMap.add("username", "student");
        linkedMultiValueMap.add("password", "Invalid_password");

        mockMvc.perform(post("/oauth/token")
            .params(linkedMultiValueMap)
            .with(SecurityMockMvcRequestPostProcessors.httpBasic(CLIENT_ID, CLIENT_SECRET))
            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error_description").value("Bad credentials"));

        mockMvc.perform(post("/oauth/token")
                .params(linkedMultiValueMap)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("InvalidClientId", CLIENT_SECRET))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGetNewAccessToken() throws Exception {
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("grant_type", "password");
        linkedMultiValueMap.add("username", "student");
        linkedMultiValueMap.add("password", "password");

        MvcResult mvcResult = mockMvc.perform(post("/oauth/token")
                .params(linkedMultiValueMap)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String refresh_token = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.refresh_token");

        LinkedMultiValueMap linkedMultiValueMap1 = new LinkedMultiValueMap<>();
        linkedMultiValueMap1.add("grant_type", "refresh_token");
        linkedMultiValueMap1.add("refresh_token", refresh_token);

        mockMvc.perform(post("/oauth/token")
            .params(linkedMultiValueMap1)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(CLIENT_ID, CLIENT_SECRET))
            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.refresh_token").exists());
    }

    @Test
    public void shouldNotGetNewAccessToken() throws Exception{
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("grant_type", "password");
        linkedMultiValueMap.add("username", "student");
        linkedMultiValueMap.add("password", "password");

        MvcResult mvcResult = mockMvc.perform(post("/oauth/token")
                .params(linkedMultiValueMap)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        LinkedMultiValueMap linkedMultiValueMap1 = new LinkedMultiValueMap<>();
        linkedMultiValueMap1.add("grant_type", "refresh_token");
        linkedMultiValueMap1.add("refresh_token", "invalidrefreshtoken");

        mockMvc.perform(post("/oauth/token")
                .params(linkedMultiValueMap1)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.error").value("invalid_token"));
    }
}
