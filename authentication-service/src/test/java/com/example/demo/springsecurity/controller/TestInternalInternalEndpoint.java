package com.example.demo.springsecurity.controller;

import com.example.demo.springsecurity.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class TestInternalInternalEndpoint {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenInternalUsers_whenGetInternalMessageWithValidUser_thenOk() {
        ResponseEntity<String> result
                = makeRestCallToGetInternalMessage("internalUser", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Hello member");
    }

    @Test
    public void givenExternalUsers_whenGetInternalMessageWithValidUser_thenOK() {
        ResponseEntity<String> result
                = makeRestCallToGetInternalMessage("externalUser", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Hello member");
    }

    @Test
    public void givenAuthProviders_whenGetInternalMessageWithNoCred_then401() {
        ResponseEntity<String> result = makeRestCallToGetInternalMessage();
        assertThat(result.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void givenAuthProviders_whenGetInternalMessageWithBadCred_then401() {
        ResponseEntity<String> result
                = makeRestCallToGetInternalMessage("user", "bad_password");

        assertThat(result.getStatusCodeValue()).isEqualTo(401);
    }

    private ResponseEntity<String>
    makeRestCallToGetInternalMessage(String username, String password) {
        return restTemplate.withBasicAuth(username, password)
                .getForEntity("/api/member/message", String.class, Collections.emptyMap());
    }

    private ResponseEntity<String> makeRestCallToGetInternalMessage() {
        return restTemplate
                .getForEntity("/api/member/message", String.class, Collections.emptyMap());
    }
}
