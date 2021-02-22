package com.example.demo.springsecurity.config;

import com.example.demo.springsecurity.services.ConsumerService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String KEY_STORE_FILE = "example-jwt.jks";
    private static final String KEY_STORE_PASSWORD = "example-pass";
    private static final String KEY_ALIAS = "example-oauth-jwt";
    private static final String JWK_KID = "example-key-id";
    private final boolean jwtEnabled;
    private final AuthenticationManager authenticationManager;

    private final ConsumerService consumerService;

    @Autowired
    public AuthorizationServerConfiguration(
            final AuthenticationConfiguration authenticationConfiguration,
            final ConsumerService consumerService,
            @Value("${security.oauth2.authorizationserver.jwt.enabled:true}") boolean jwtEnabled) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.consumerService = consumerService;
        this.jwtEnabled = jwtEnabled;
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // @formatter:off
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore());

        if (this.jwtEnabled) {
            endpoints
                    .accessTokenConverter(accessTokenConverter());
        }
        // @formatter:on
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(consumerService);
    }

    @Bean
    public TokenStore tokenStore() {
        if (this.jwtEnabled) {
            return new JwtTokenStore(accessTokenConverter());
        } else {
            return new InMemoryTokenStore();
        }
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
        JwtAccessTokenConverter converter = new JwtCustomHeadersAccessTokenConverter(customHeaders, keyPair());

        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        converter.setAccessTokenConverter(accessTokenConverter);

        return converter;
    }

    @Bean
    public KeyPair keyPair() {
        ClassPathResource ksFile = new ClassPathResource(KEY_STORE_FILE);
        KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, KEY_STORE_PASSWORD.toCharArray());
        return ksFactory.getKeyPair(KEY_ALIAS);
    }

    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic()).keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(JWK_KID);
        return new JWKSet(builder.build());
    }

}
