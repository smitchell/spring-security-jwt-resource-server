package com.example.gateway.api;

import com.example.gateway.controllers.SecurityController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class GatewayApi {

    private final SecurityController securityController;

    @Autowired
    public GatewayApi(SecurityController securityController) {
        this.securityController = securityController;
    }

    @GetMapping("/api/exchangeToken/{authorizationCode}")
    public ResponseEntity<String> exchangeToken(@PathVariable final String authorizationCode) {
        log.info("authenticate <-- ".concat(authorizationCode));
        Optional<String> tokenOption = securityController.exchangeToken(authorizationCode);
        String token = "";
        if (tokenOption.isPresent()) {
            token = tokenOption.get();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(token);
    }

}
