package com.example.demo.springsecurity.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CustomMessage {
    private String message;
    private List<String> roles;
}
