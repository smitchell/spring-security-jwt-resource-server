package com.medzero.service.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers(
            "/phones/search/findByNumberAndActiveTrue**",
            "/emails/search/findByAddressAndActiveTrue**")
        .permitAll()
        .and()
        .authorizeRequests().anyRequest().authenticated()
        .and();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId("member-service");
  }

}
