package com.example.gateway;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.*;

import io.restassured.http.Header;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * This Live Test requires:
 * - the Authorization Server to be running
 * - the Resource Server to be running
 *
 */
public class JWTResourceServerLiveTest {
    private final String redirectUrl = "http://localhost:8080/";
    private final String authorizeUrlPattern = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect/auth?response_type=code&client_id=fooClient&scope=%s&redirect_uri=" + redirectUrl;
    private final String tokenUrl = "http://localhost:8083/auth/token";
    private final String resourceUrl = "http://localhost:8081/resource-server-jwt/foos";
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void givenUserWithReadScope_whenGetFooResource_thenSuccess() {
//        String accessToken = obtainAccessToken("read");
//
//        // Access resources using access token
//        Response response = RestAssured.given()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .get(resourceUrl);
//        assertThat(response.as(List.class)).hasSizeGreaterThan(0);
//    }
//
//    private String obtainAccessToken(String scopes) {
//        // obtain authentication url with custom codes
//        Response response = RestAssured.given()
//                .redirects()
//                .follow(false)
//                .get(String.format(authorizeUrlPattern, scopes));
//        String authSessionId = response.getCookie("AUTH_SESSION_ID");
//        String kcPostAuthenticationUrl = response.asString()
//                .split("action=\"")[1].split("\"")[0].replace("&amp;", "&");
//
//        // obtain authentication code and state
//        response = RestAssured.given()
//                .redirects()
//                .follow(false)
//                .cookie("AUTH_SESSION_ID", authSessionId)
//                .formParams("username", "john@test.com", "password", "123", "credentialId", "")
//                .post(kcPostAuthenticationUrl);
//        assertThat(HttpStatus.FOUND.value()).isEqualTo(response.getStatusCode());
//
//        // extract authorization code
//        String location = response.getHeader(HttpHeaders.LOCATION);
//        String code = location.split("code=")[1].split("&")[0];
//
//        // get access token
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("grant_type", "authorization_code");
//        params.put("code", code);
//        params.put("client_id", "fooClient");
//        params.put("redirect_uri", redirectUrl);
//        params.put("client_secret", "fooClientSecret");
//        response = RestAssured.given()
//                .formParams(params)
//                .post(tokenUrl);
//        return response.jsonPath()
//                .getString("access_token");
//    }

}
