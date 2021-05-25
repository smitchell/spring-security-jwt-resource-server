package com.example.demo.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Order(-10)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
        http
                .requestMatchers(requestMatcher -> {
                    requestMatcher.antMatchers("/login", "/logout", "/oauth/authorize");
                })
                .authorizeRequests(authorize -> {
                    authorize.mvcMatchers("/.well-known/jwks.json").permitAll();
                })
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin(formLogin -> {
                    formLogin.loginPage("/login").permitAll();
                    formLogin.defaultSuccessUrl("http://localhost:4200/authorized", true);
                })
                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                    logout.logoutSuccessUrl("http://localhost:4200/");
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID");
                    logout.permitAll();
                })
                .csrf(csrf -> {
                    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                    csrf.ignoringRequestMatchers((request) -> "/introspect".equals(request.getRequestURI()));
                });
    }

}


