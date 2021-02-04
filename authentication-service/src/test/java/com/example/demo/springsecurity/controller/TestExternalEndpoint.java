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
public class TestExternalEndpoint {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenExternalUsers_whenGetExternalMessageWithValidUser_thenOK() {
        ResponseEntity<String> result
                = makeRestCallToGetExternalMessage("externalUser", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Hello member");
    }

    @Test
    public void givenInternalUsers_whenGetExternalMessageWithValidUser_thenOK() {
        ResponseEntity<String> result
                = makeRestCallToGetExternalMessage("internalUser", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("Hello member");
    }

    @Test
    public void givenAuthProviders_whenGetExternalMessageWithNoCred_then401() {
        ResponseEntity<String> result = makeRestCallToGetExternalMessage();
        assertThat(result.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void givenAuthProviders_whenGetExternalMessageWithBadCred_then401() {
        ResponseEntity<String> result
                = makeRestCallToGetExternalMessage("user", "bad_password");

        assertThat(result.getStatusCodeValue()).isEqualTo(401);
    }

    private ResponseEntity<String>
    makeRestCallToGetExternalMessage(String username, String password) {
        return restTemplate.withBasicAuth(username, password)
                .getForEntity("/api/member/message", String.class, Collections.emptyMap());
    }

    private ResponseEntity<String> makeRestCallToGetExternalMessage() {
        return restTemplate
                .getForEntity("/api/member/message", String.class, Collections.emptyMap());
    }


}
