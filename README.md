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
the browser's session storage. If none is found, it will redirect the user to the authentication service custom login page. 

This is the login flow:
1.	The frontend posts the credentials to /oauth/authorize (instead of /login) to get back the authorization code.
2.	The authorization service redirects to the /authorized page.
3.	The authorized page posts the state and code parameters to the Gateway.
4.	The Gateway posts the code to the Auth Service using its Oauth2 client credentials.
5.	The auth service returns an encrypted JWT token.
6.	The Gateway returns the token to the frontend.
7.	The frontend caches the JWT token.


On the home page is a button that calls a protected endpoint on the gateway service to get its build information and display it on the page.


This is the resource server flow:
1.	The user logs in
2.	They system displays the homepage.
3.	The user clicks the Load Gateway Message button.
4.	The system HTTP Interceptor places the cached JWT token in the Authorization header of the GET to the Gateway.
5.	The Gateway Spring Security resource service configuration uses the JWKS endpoint to decode and validate the JWT token.
6.	The JWT token is verified.
7.	Spring Security allows the request to reach the Gateway message endpoint.
8.	The Gateway returns the message.
9.	The Frontend displays the message on the web page.

```

# REFERENCES
* https://github.com/spring-guides/gs-authenticating-ldap
* https://memorynotfound.com/spring-boot-spring-ldap-integration-testing-example/
* https://github.com/spring-projects/spring-security/tree/master/samples/boot/oauth2authorizationserver
* https://github.com/spring-projects/spring-security/tree/master/samples/boot/oauth2resourceserver
* https://www.baeldung.com/spring-security-oauth-resource-server
* https://www.baeldung.com/spring-security-oauth2-jws-jwk
