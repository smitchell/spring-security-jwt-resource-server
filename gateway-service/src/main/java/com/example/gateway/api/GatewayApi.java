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

    @GetMapping("/gatewayMessage")
    public GatewayMessage getGatewayMessage() {
        return new GatewayMessage("1.0", "The gateway says hello");
    }

}
