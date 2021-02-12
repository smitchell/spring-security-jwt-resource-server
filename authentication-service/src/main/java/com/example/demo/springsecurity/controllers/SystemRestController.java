package com.example.demo.springsecurity.controllers;

import com.example.demo.springsecurity.models.CustomMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SystemRestController {


    private static List<String> getRoleNames() {
        List<String> roles = new ArrayList();
        for (GrantedAuthority authority :
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

    @RolesAllowed("ROLE_EMPLOYEE")
    @GetMapping("/api/employee/message")
    public CustomMessage getEmployeeMessage(Principal principal) {
        return new CustomMessage("Hello " + principal.getName(), getRoleNames());
    }

    @RolesAllowed({"ROLE_EMPLOYEE", "ROLE_CUSTOMER"})
    @GetMapping("/api/customer/message")
    public CustomMessage getCustomerMessage(Principal principal) {
        return new CustomMessage("Hello " + principal.getName(), getRoleNames());
    }

}
