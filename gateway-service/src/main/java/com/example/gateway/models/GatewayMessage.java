package com.example.gateway.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GatewayMessage {
    private String version;
    private String message;
}
