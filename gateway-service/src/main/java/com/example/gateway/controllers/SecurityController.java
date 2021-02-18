package com.example.gateway.controllers;

import com.example.gateway.models.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final String authenticationServerUrl;

    private final String resourceClientId;

    private final String resourceClientSecret;

    @Autowired
    public SecurityController(
            @Value("${resource-client-id}") final String resourceClientId,
            @Value("${resource-client-secret}") final String resourceClientSecret,
            @Value("${authorization-server-url}") final String authenticationServerUrl
    ) {
        this.authenticationServerUrl = authenticationServerUrl;
        this.resourceClientId = resourceClientId;
        this.resourceClientSecret = resourceClientSecret;
    }

    public RestTemplate clientRestTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthorization("gateway_client", "client_secret").build();
    }

    public Optional<JwtToken> exchangeToken(final String authorizationCode) {
        log.info("exchangeToken <-- ".concat(authorizationCode));
        final RestTemplate restTemplate = new RestTemplate();

        // create auth credentials
        final String authStr = this.resourceClientId.concat(":").concat(resourceClientSecret);
        final String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic ".concat(base64Creds));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.authenticationServerUrl.concat("/oauth/token"))
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authorizationCode)
                .queryParam("client_Id", resourceClientId)
                .queryParam("state", RandomStringUtils.random(5));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            log.info("exchangeToken POST to  ".concat(builder.toUriString()));
            HttpEntity<JwtToken> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity,
                    JwtToken.class);
            if (response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (Exception e) {
            log.error("Authentication Error", e);
        }
        return Optional.empty();
    }

}
