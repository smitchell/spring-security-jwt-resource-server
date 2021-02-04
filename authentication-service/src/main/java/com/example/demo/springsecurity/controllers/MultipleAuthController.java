package com.example.demo.springsecurity.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultipleAuthController {

    @PreAuthorize("hasRole('ROLE_INTERNAL')")
    @GetMapping("/api/message")
    public String getMessage() {
        for (GrantedAuthority authority :
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            System.out.println(authority.getAuthority());
        }
        return "OK";
    }

    @PreAuthorize("hasRole('ROLE_INTERNAL') or hasRole('ROLE_EXTERNAL')")
    @GetMapping("/api/member/message")
    public String getMemberMessage() {
        for (GrantedAuthority authority :
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            System.out.println(authority.getAuthority());
        }
        return "Hello member";
    }

}
