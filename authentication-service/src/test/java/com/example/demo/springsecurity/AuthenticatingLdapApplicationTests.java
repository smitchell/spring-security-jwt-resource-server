/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.springsecurity;

import com.example.demo.springsecurity.domain.Consumer;
import com.example.demo.springsecurity.services.ConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Rob Winch
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticatingLdapApplicationTests {
    @MockBean
    ClientDetails clientDetails;

    @MockBean
    ConsumerService consumerService;

    @Autowired
    private MockMvc mockMvc;

    private static final String USER_NAME = "john";
    private static final String PASSWORD = "demoPa55";

    @BeforeEach
    public void before() {
        Set<String> scopes = new HashSet<>();
        scopes.add("read");
        scopes.add("write");
        Set<String> grants = new HashSet<>();
        grants.add("password");
        grants.add("refresh_token");
        when(this.clientDetails.getScope()).thenReturn(scopes);
        when(this.clientDetails.getAccessTokenValiditySeconds()).thenReturn(100);
        when(this.clientDetails.getAuthorizedGrantTypes()).thenReturn(grants);
        when(this.clientDetails.getRefreshTokenValiditySeconds()).thenReturn(100);
        when(this.clientDetails.getClientId()).thenReturn("dummy-client");
        when(this.clientDetails.isSecretRequired())
                .thenReturn(Boolean.TRUE);
        when(this.clientDetails.getClientSecret())
                .thenReturn(new BCryptPasswordEncoder().encode("client-secret"));

        Consumer consumer = new Consumer();
        consumer.setScopeCsv("read,write");
        consumer.setAuthorizedGrantTypesCsv("password,refresh_token,authorization_code");
        consumer.setAccessTokenValiditySeconds(100);
        consumer.setRefreshTokenValiditySeconds(100);
        consumer.setClientId("dummy-client");
        consumer.setRegisteredRedirectUrisCsv("https://test.domain.com/context1,https://test.domain.com/context2,https://test.domain.com/context3");
        consumer.setClientSecret(new BCryptPasswordEncoder().encode("client-secret"));
        this.clientDetails = consumer;

        Mockito.when(this.consumerService.loadClientByClientId("dummy-client"))
                .thenReturn(clientDetails);
    }



    @Test
    public void authorizationRedirects() throws Exception {
        MvcResult result = mockMvc.perform(get("/oauth/authorize"))
                .andExpect(status().isFound())
//        .andExpect(header().string("Location", "http://localhost:8080/login"))
                .andExpect(header().string("Location", "http://localhost/login"))
                .andReturn();
    }

    @Test
    public void loginSucceeds() throws Exception {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("username", USER_NAME);
        form.set("password", PASSWORD);

        mockMvc.perform(post("/login")
                .params(form).with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("http://localhost:4200/authorized"))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void authorizationRedirectsAfterLogin() throws Exception {
        mockMvc.perform(get("/oauth/authorize")
                .param("client_id", "dummy-client")
                .param("response_type", "code")
                .param("redirect_uri", "https://test.domain.com/context2")
                .with(user(USER_NAME)))
                .andExpect(forwardedUrl("/oauth/confirm_access"));
    }

    @Test
    public void getJwksKeys() throws Exception {
        mockMvc.perform(get("/.well-known/jwks.json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.keys", hasSize(1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.keys[0].kty").value("RSA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keys[0].e").value("AQAB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keys[0].kid").value("example-key-id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keys[0].alg").value("RS256"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * The purpose of this test is to verify that CSRF is being populated on the custom login form.
     * <pre>
     *       &lt;input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}" /&gt;
     * </pre>
     */
    @Test
    public void verifyCors() throws Exception {
        MvcResult loginPage = mockMvc.perform(get("/login"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();


        String cors = getCorsId(loginPage.getResponse().getContentAsString());
        assertNotNull(cors);
    }

    private String getCorsId(String soup) {
        Matcher matcher = Pattern.compile("(?s).*name=\"_csrf\".*?value=\"([^\"]+).*")
                .matcher(soup);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    @Test
    public void loginFailure() throws Exception {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("username", "notUser");
        form.set("password", "notpassword");

        mockMvc.perform(post("/login")
                .params(form).with(csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(header().string("Location", "/login?error"))
                .andReturn();
    }

    @Test
    public void loginWithValidEmployeeUserThenAuthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user(USER_NAME)
                .password(PASSWORD);

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername(USER_NAME));
    }

    @Test
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        FormLoginRequestBuilder login = formLogin()
                .user("invalid")
                .password("invalidpassword");

        mockMvc.perform(login)
                .andExpect(unauthenticated());
    }
}
