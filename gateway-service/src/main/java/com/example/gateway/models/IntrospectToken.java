package com.example.gateway.models;

import lombok.Data;

import java.util.Date;

@Data
public class IntrospectToken {
    private String sub;
    private String scope;
    private boolean active;
    private Date exp;
}
