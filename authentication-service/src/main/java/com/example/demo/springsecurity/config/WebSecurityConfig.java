package com.example.demo.springsecurity.config;

import com.example.demo.springsecurity.filter.JwtAuthenticationFilter;
import com.example.demo.springsecurity.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(-10)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final String privateKey;

    @Autowired
    public WebSecurityConfig(
            @Value("${keypair.private-key}") final String privateKey) {
        this.privateKey = privateKey;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/webjars/**",
                "/css/**",
                "/images/**",
                "/favicon.ico");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .requestMatchers()
                .antMatchers("/",  "/oauth", "/login",  "/api/authenticate", "/oauth/authorize")
                .and()
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("http://localhost:4200/authorized", true)
                .and()
            .logout()
                .logoutSuccessUrl("http://localhost:4200/")
                .permitAll()
                .and()
            .addFilter(new JwtAuthenticationFilter(privateKey, authenticationManager()))
            .addFilter(new JwtAuthorizationFilter(privateKey, authenticationManager()));
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // @formatter:on
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
