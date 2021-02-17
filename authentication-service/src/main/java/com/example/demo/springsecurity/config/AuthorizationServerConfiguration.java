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
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    private final ConsumerService consumerService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationServerConfiguration(
            final ConsumerService consumerService,
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.consumerService = consumerService;
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(consumerService);
    }

//    @Override
//    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints
//                .authenticationManager(authenticationManager);
//                .accessTokenConverter(jwtAccessTokenConverter)
//                .tokenStore(tokenStore(jwtAccessTokenConverter));
//
//        //Invalidate the session once the user has been authenticated
//        endpoints.addInterceptor(new HandlerInterceptorAdapter() {
//            @Override
//            public void postHandle(HttpServletRequest request,
//                                   HttpServletResponse response, Object handler,
//                                   ModelAndView modelAndView) throws Exception {
//                if (modelAndView != null
//                        && modelAndView.getView() instanceof RedirectView) {
//                    RedirectView redirect = (RedirectView) modelAndView.getView();
//                    String url = redirect.getUrl();
//                    assert url != null;
//                    if (url.contains("code=") || url.contains("error=")) {
//                        HttpSession session = request.getSession(false);
//                        if (session != null) {
//                            session.invalidate();
//                        }
//                    }
//                }
//            }
//        });
//    }

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public DefaultTokenServices tokenServices(final TokenStore tokenStore,
                                              final ClientDetailsService clientDetailsService) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(this.authenticationManager);
        return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
        return new JwtCustomHeadersAccessTokenConverter(customHeaders, keyPair());
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
