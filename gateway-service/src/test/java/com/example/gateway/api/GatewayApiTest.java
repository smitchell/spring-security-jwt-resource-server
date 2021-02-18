package com.example.gateway.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class GatewayApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOkOnProjectsEndpoint() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status()
                .isOk();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/exchangeToken")
                        .requestAttr("authorizationCode", "abcd")
                        .contentType(MediaType.APPLICATION_JSON);

        // TODO - Need to mock the call to the authorization service
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}
