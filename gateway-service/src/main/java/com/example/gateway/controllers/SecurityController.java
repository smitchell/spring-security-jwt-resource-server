package com.example.gateway.controllers;

import com.example.gateway.config.TrustedRestTemplateFactory;
import com.example.gateway.models.IntrospectToken;
import com.example.gateway.models.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Slf4j
@Component
public class SecurityController {

    private final String authenticationServerUrl;

    private final String resourceClientId;

    private final RestTemplate trustedRestTemplate;

    @Autowired
    public SecurityController(
            @Value("${resource-client-id}") final String resourceClientId,
            @Value("${authorization-server-url}") final String authenticationServerUrl,
            TrustedRestTemplateFactory trustedRestTemplateFactory
    ) {
        this.authenticationServerUrl = authenticationServerUrl;
        this.resourceClientId = resourceClientId;
        this.trustedRestTemplate = trustedRestTemplateFactory.getObject();
    }

    public RestTemplate clientRestTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthentication("gateway_client", "client_secret").build();
    }

    public Optional<JwtToken> exchangeToken(final String authorizationCode, final String state) {
        log.info("exchangeToken <-- ".concat(authorizationCode));
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.authenticationServerUrl.concat("/oauth/token"))
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authorizationCode)
                .queryParam("client_Id", resourceClientId)
                .queryParam("state", state)
                .queryParam("redirect_uri", "http://localhost:4200/authorized");

        return getAuthToken(builder, this.trustedRestTemplate);
    }

    public Optional<JwtToken> refreshToken(final JwtToken jwtToken) {
        log.info("refreshToken");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.authenticationServerUrl.concat("/oauth/token"))
                .queryParam("grant_type", "refresh_token")
                .queryParam("client_Id", resourceClientId)
                .queryParam("refresh_token", jwtToken.getRefresh_token());

        return getAuthToken(builder, this.trustedRestTemplate);
    }

    private Optional<JwtToken> getAuthToken(UriComponentsBuilder builder, RestTemplate restTemplate) {
        try {
            log.info("getAuthToken POST to  ".concat(builder.toUriString()));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity<HttpHeaders> httpEntity = new HttpEntity(httpHeaders);
            ResponseEntity<JwtToken> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    httpEntity,
                    JwtToken.class);
            if (response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (Exception e) {
            log.error("Authentication Error", e);
        }
        return Optional.empty();
    }

    public Optional<IntrospectToken> introspectToken(String token) {
        log.info("introspectToken <-- ".concat(token));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.authenticationServerUrl.concat("/introspect"))
                .queryParam("token", token);
        try {
            log.info("introspect GET to  ".concat(builder.toUriString()));
            ResponseEntity<IntrospectToken> response = this.trustedRestTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    null,
                    IntrospectToken.class);
            if (response.getBody() != null) {
                return Optional.of(response.getBody());
            }
        } catch (Exception e) {
            log.error("Authentication Error", e);
        }
        return Optional.empty();
    }
}
