package com.example.demo.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class LdapAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    private final String ldapSearchBase = "ou=people";
    private final String ldapSearchFilter = "uid={0}";
    private final String ldapUrl = "ldap://localhost:8389/dc=example,dc=com";

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
            .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .userSearchBase(ldapSearchBase)
                .userSearchFilter(ldapSearchFilter)
                .groupSearchBase("ou=groups")
                .groupSearchFilter("uniqueMember={0}")
                .groupRoleAttribute("ou")
            .contextSource()
                .url(ldapUrl)
                .and()
            .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LdapUserDetailsService ldapUserDetailsService() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setReferral("follow");
        contextSource.afterPropertiesSet();
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(ldapSearchBase, ldapSearchFilter, contextSource);
        LdapUserDetailsService service = new LdapUserDetailsService(userSearch);
        service.setUserDetailsMapper(new LdapUserDetailsMapper());
        return service;
    }
}
