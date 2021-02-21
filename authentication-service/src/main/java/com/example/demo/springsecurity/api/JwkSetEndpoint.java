package com.example.demo.springsecurity.api;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Legacy Authorization Server (spring-security-oauth2) does not support any
 * <a href target="_blank" href="https://tools.ietf.org/html/rfc7517#section-5">JWK Set</a> endpoint.
 *
 * This class adds ad-hoc support in order to better support the other samples in the repo.
 */
@FrameworkEndpoint
class JwkSetEndpoint {

    private final JWKSet jwkSet;

    public JwkSetEndpoint(final JWKSet jwkSet) {
        this.jwkSet = jwkSet;
    }

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        return this.jwkSet.toJSONObject();
    }
}
