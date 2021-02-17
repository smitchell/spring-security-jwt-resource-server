package com.example.gateway.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Optional;

@Slf4j
@Component
public class SecurityController {

    private final String base_url = "http://localhost:5000";

    @Autowired
    public SecurityController() {
    }

    public RestTemplate clientRestTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthorization("gateway_client", "client_secret").build();
    }

    public Optional<String> exchangeToken(final String authorizationCode) {
        log.info("exchangeToken <-- ".concat(authorizationCode));
        final RestTemplate restTemplate = new RestTemplate();

        // create auth credentials
        final String authStr = "gateway-client:client-secret";
        final String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic ".concat(base64Creds));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.base_url.concat("/oauth/token"))
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authorizationCode)
                .queryParam("client_Id", "clientId")
                .queryParam("state", RandomStringUtils.random(5));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            log.info("exchangeToken POST to  ".concat(builder.toUriString()));
            HttpEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            if (response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (Exception e) {
            log.error("Authentication Error", e);
        }
        return Optional.empty();
    }

}
