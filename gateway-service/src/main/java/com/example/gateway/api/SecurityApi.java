package com.example.gateway.api;

import com.example.gateway.controllers.SecurityController;
import com.example.gateway.models.IntrospectToken;
import com.example.gateway.models.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class SecurityApi {

    private final SecurityController securityController;

    @Autowired
    public SecurityApi(
            final SecurityController securityController) {
        this.securityController = securityController;
    }

    @GetMapping("/exchangeToken")
    public ResponseEntity<JwtToken> exchangeToken(@RequestParam final String authorizationCode, @RequestParam final String state) {
        log.info("exchangeToken <-- ".concat(authorizationCode));
        Optional<JwtToken> tokenOption = securityController.exchangeToken(authorizationCode, state);
        if (tokenOption.isPresent()) {
            log.info("exchangeToken - Exchanging the short-lived token for a longer lived access token");
            Optional<JwtToken> refreshToken = securityController.refreshToken(tokenOption.get());
            log.info("exchangeToken - Refresh token present is " + refreshToken.isPresent());
            return refreshToken.map(jwtToken -> ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jwtToken)).orElseGet(() -> ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(tokenOption.get()));
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null);
    }


    @GetMapping("/refreshToken")
    public ResponseEntity<JwtToken> refreshToken(@RequestBody JwtToken jwtToken) {
        log.info("refreshToken <-- ".concat(jwtToken.getRefresh_token()));
        Optional<JwtToken> optionalToken = securityController.refreshToken(jwtToken);

        return optionalToken.map(refreshToken -> ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(refreshToken)).orElseGet(() -> ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null));
    }

    @GetMapping("/introspectToken")
    public ResponseEntity<IntrospectToken> introspectToken(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("introspectToken <-- ".concat(authorizationHeader));
        String token = authorizationHeader.split(" ")[1];
        Optional<IntrospectToken> optionalToken = securityController.introspectToken(token);
        return optionalToken.map(introspectToken -> ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(introspectToken)).orElseGet(() -> ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null));
    }

}
