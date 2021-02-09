package com.example.demo.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * The purpose of this test is to generate encoded strings
 */
public class LdapPasswordEncoderTest {

    @Test
    public void testLdapShaPasswordEncoder() {
        org.springframework.security.crypto.password.PasswordEncoder encoder
                = new LdapShaPasswordEncoder();
        String password = "demoPa55";
        String encodedPassword = encoder.encode(password);
        System.out.println(password.concat(" = ").concat(encodedPassword));
        assertTrue(encoder.matches(password, encodedPassword));

    }
}
