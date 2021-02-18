package com.example.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("development")
public class WebMvcController implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // ONLY FOR TESTING
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    }

}
