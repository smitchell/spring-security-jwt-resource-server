package com.example.gateway.api;

import com.example.gateway.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
public class GatewayApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOkOnExchangeTokenEndpoint() throws Exception {
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

    @Test
    @WithMockUser
    public void shouldReturnOkOnBuildPropertiesEndpoint() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status()
                .isOk();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/api/buildInfo")
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").value("0.0.1-SNAPSHOT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.group").value("com.example"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artifact").value("gateway-service"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("gateway"))
                .andReturn().getResponse().getContentAsString();
    }

}
