package com.example.demo.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    CustomerAuthenticationProvider customerAuthenticationProvider;

    @Autowired
    EmployeeAuthenticationProvider employeeAuthenticationProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customerAuthenticationProvider);
        auth.authenticationProvider(employeeAuthenticationProvider);
        // @formatter:off
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .userSearchBase("ou=people")
                .userSearchFilter("uid={0}")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("uniqueMember={0}")
                .contextSource()
                .url("ldap://localhost:8389/dc=example,dc=com")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin();
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new LdapShaPasswordEncoder();
    }
}

