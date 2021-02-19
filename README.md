# SPRING SECURITY POC

This project demonstrates using Spring Security to provide JWT token to secure a resource server.
In this case, the authentication service is a legacy server running Spring 5.1. The resource server
is new, running the latest Spring Security.

## LDAP
The authentication server uses an in-memory LDAP server with to users:
* john/demoPa55
* jane/demoPa55

## Projects
There are three projects:

1) authentication-service - Run "mvn spring-boot:run" to start. Health check is http://localhost:5000/actuator/health
2) gateway-service - Run "mvn spring-boot:run" to start. Health check is http://localhost:5005/actuator/health
3) web-frontend - Run "ng servce" to start. Open your browser to http://localhost:4200/. Sign in as "john" or "jane", "demoPa55"

## Sign-in
The URL http://localhost:4200/ is the Home Component. It has an Angular Auth Guard defined that looks for a non-expired token in
the browser's session storage. If none is found, it will redirect the user to the authentication service custom login page. After successfully authenticating,
the user is redirected to the frontend Authorized page, which will redirect the user back to the authentication server to get an authorization code.
That is an extra hop, but the custom login page does not return the JWT token, so that is what was needed to be authenticated in order to
request the authorization code.

Once the user is redirected back with an authorization code, the system calls the gateway to exchange if for an access token. The frontend
stores the access token is the browser's session storage, and then redirects the authenticated user to the home page. The authentication server is setup with
a self-signed keystore that it uses to generate a JWT signing key to encryt the access token that it returns.

On the home page is a button that calls a protected endpoint on the gateway service to get its build information and display it on the page.
The frondend application includes an HTTP interceptor that automatically attaches an AUTHORIZATION header to all REST calls if a token is
found in the browser's session storage.

Finally, the gateway service is configured as a Spring Security Resource Server. I calls the "jwks" endpoint on the authentication service to get the signing key.
Spring Security uses the signing key to verify the JWT token.

```

# REFERENCES
* https://github.com/spring-guides/gs-authenticating-ldap
* https://www.baeldung.com/spring-security-multiple-auth-providers
* https://memorynotfound.com/spring-boot-spring-ldap-integration-testing-example/
* https://www.baeldung.com/spring-security-oauth-resource-server
* https://www.baeldung.com/spring-security-oauth2-jws-jwk
