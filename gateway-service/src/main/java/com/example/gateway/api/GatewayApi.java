package com.example.gateway.api;

import com.example.gateway.controllers.SecurityController;
import com.example.gateway.models.GatewayMessage;
import com.example.gateway.models.JwtToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class GatewayApi {

    private final SecurityController securityController;

    @Autowired
    public GatewayApi(
            final SecurityController securityController) {
        this.securityController = securityController;
    }

    @GetMapping("/exchangeToken")
    public ResponseEntity<String> exchangeToken(@RequestParam final String authorizationCode) {
        log.info("authenticate <-- ".concat(authorizationCode));
        Optional<JwtToken> tokenOption = securityController.exchangeToken(authorizationCode);
        String token = null;
        if (tokenOption.isPresent()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                token = mapper.writeValueAsString(tokenOption.get());
            } catch (JsonProcessingException e) {
                log.warn(e.getMessage());
            }
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(token);
    }

    @GetMapping("/api/gatewayMessage")
    public GatewayMessage getGatewayMessage() {
        return new GatewayMessage("1.0", "The gateway say hello");
    }

}
