package com.example.demo.springsecurity.controller;

import com.example.demo.springsecurity.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class TestExternalEndpoint {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void givenValidMemberUser_getMemberMessageEndpointshouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("externalUser", "pass")
                .getForEntity("/api/member/message", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenValidEmployeeUser_getMemberMessageshouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("internalUser", "pass")
                .getForEntity("/api/member/message", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
