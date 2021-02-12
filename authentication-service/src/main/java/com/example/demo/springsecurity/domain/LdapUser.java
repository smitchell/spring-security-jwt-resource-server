package com.example.demo.springsecurity.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import java.util.Collection;

public class LdapUser implements LdapUserDetails {
    private String commonName;
    private final LdapUserDetails ldapUserDetails;

    public LdapUser(LdapUserDetails ldapUserDetails) {
        this.ldapUserDetails = ldapUserDetails;
    }

    @Override
    public String getDn() {
        return ldapUserDetails.getDn();
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ldapUserDetails.getAuthorities();
    }

    @Override
    public String getPassword() {
        return ldapUserDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return ldapUserDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return ldapUserDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return ldapUserDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return ldapUserDetails.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return ldapUserDetails.isEnabled();
    }
}
