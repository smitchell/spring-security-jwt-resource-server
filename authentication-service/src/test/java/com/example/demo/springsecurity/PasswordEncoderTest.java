package com.example.demo.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The purpose of this test is to generate encoded strings
 */
public class PasswordEncoderTest {

    private final String password = "demoPa55";

    @Test
    public void testBCryptPasswordEncoder() {
        org.springframework.security.crypto.password.PasswordEncoder encoder
                = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        System.out.println(password.concat(" = ").concat(encodedPassword));
        assertTrue(encoder.matches(password, encodedPassword));

    }

    @Test
    public void testLdapShaPasswordEncoder() {
        org.springframework.security.crypto.password.PasswordEncoder encoder
                = new LdapShaPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        System.out.println(password.concat(" = ").concat(encodedPassword));
        assertTrue(encoder.matches(password, encodedPassword));

    }
}
