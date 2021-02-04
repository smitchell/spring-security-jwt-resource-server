package com.example.demo.springsecurity.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class ExternalAuthenticationProviderTest {

    @Autowired
    private ExternalAuthenticationProvider externalAuthenticationProvider;

    @Test
    public void testAuthenticateGoodCredentials() {
        Authentication auth = new UsernamePasswordAuthenticationToken("externalUser", "pass");
        externalAuthenticationProvider.authenticate(auth);
    }

    @Test
    public void testAuthenticateBadCredentials() {
        Authentication auth = new UsernamePasswordAuthenticationToken("nobody", "nothing");
        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            externalAuthenticationProvider.authenticate(auth);
        });

        String expectedMessage = "External system authentication failed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSupportsUsernamePasswordAuthenticationToken() {
        Authentication auth = new UsernamePasswordAuthenticationToken("externalUser", "pass");
        assertTrue(externalAuthenticationProvider.supports(auth.getClass()));
    }
}
