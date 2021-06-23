package com.example.gateway.api;

import com.example.gateway.controllers.SecurityController;
import com.example.gateway.models.GatewayMessage;
import com.example.gateway.models.IntrospectToken;
import com.example.gateway.models.JwtToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class GatewayApi {

    private final SecurityController securityController;

    @Autowired
    public GatewayApi(
            final SecurityController securityController) {
        this.securityController = securityController;
    }

    @GetMapping("/exchangeToken")
    public ResponseEntity<String> exchangeToken(@RequestParam final String authorizationCode, @RequestParam final String state) {
        log.info("authenticate <-- ".concat(authorizationCode));
        Optional<JwtToken> tokenOption = securityController.exchangeToken(authorizationCode, state);
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

    @GetMapping("/gatewayMessage")
    public GatewayMessage getGatewayMessage() {
        return new GatewayMessage("1.0", "The gateway says hello");
    }

}
