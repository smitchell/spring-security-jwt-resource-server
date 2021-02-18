package com.example.gateway.models;

import lombok.Data;

@Data
public class JwtToken {
    private String access_token;
    private String token_type;
    private long expires_in;
    private String refresh_token;
    private String scope;
}
