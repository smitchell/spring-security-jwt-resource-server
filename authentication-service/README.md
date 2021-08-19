*HOW TO RUN THIS PROJECT*

mvn spring-boot:run

*STEPS TO REPRODUCE REFRESH TOKEN PROBLEM*

1) Paste this is a browser to get the auth code:
     
[http://localhost:8090/oauth/authorize?response_type=code&client_id=gateway-client&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fauthorized](http://localhost:8090/oauth/authorize?response_type=code&client_id=gateway-client&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fauthorized)

You will be prompted to login, then a code is returned in the URL    
    
    


2) Exchange the auth code for a token

Export the auth code.

```
export AUTH_CODE=zQuOTm
```

Run this curl command:

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" \
--user "$CLIENT_ID:$CLIENT_SECRET" \
-d "grant_type=authorization_code&code=$AUTH_CODE&client_id=gateway-client&client_secret=client-secret&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fauthorized" \
http://localhost:8090/oauth/token
```

You will receive a JWT token. Export its access token value an its refresh token value.

```
export ACCESS_TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImV4YW1wbGUta2V5LWlkIn0.eyJleHAiOjE2MjkzOTIxODksInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiLCJST0xFX0VNUExPWUVFIl0sImp0aSI6IjMyZjgzMTFkLTY3MDktNDU1Yy04OTJlLTY2MjFjMThkZDdjZCIsImNsaWVudF9pZCI6ImdhdGV3YXktY2xpZW50Iiwic2NvcGUiOlsidHJ1c3QiLCJyZWFkIiwid3JpdGUiXX0.kIjfjSyy-8ufzbF-dZn7KTNIVboJOAg2gmZ3nLdvomBGlBLBQ-i-eHMeXkIlQr1dYTDVOwsujVbyEZky0SDAZRWESnjdyN8ZaPUq5cj7sPxW83IdBD5g9haIwxPh-lnLliT9Pd0Sjphtty45dagiXyb15IeybJJDWwS8-wM8MnP5s37O9zCzYqvefPPlIHngcOPgRiO-Qojv7laTHrH3pMl2MB2tDdhSJcOqfRBj0d3m3fQJa4ACcGiYWr9NDIZjV2Xxs18VdiEq_SgqVppLnJa1PKgF9sbU2TSrnlU3X_Hi2uZruHJaQTE7dWuJbGfFkE1eJX3Bs0sBU68LmTgAtA

export REFRESH_TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImV4YW1wbGUta2V5LWlkIn0.eyJ1c2VyX25hbWUiOiJqb2huIiwic2NvcGUiOlsidHJ1c3QiLCJyZWFkIiwid3JpdGUiXSwiYXRpIjoiMzJmODMxMWQtNjcwOS00NTVjLTg5MmUtNjYyMWMxOGRkN2NkIiwiZXhwIjoxNjI5NDc0OTg5LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiLCJST0xFX0VNUExPWUVFIl0sImp0aSI6IjM1MDA1M2Q4LTY2YjQtNDQzMi04ZjFmLTVkODYyMTBjMzI4ZiIsImNsaWVudF9pZCI6ImdhdGV3YXktY2xpZW50In0.iJdm5UuzCQn1Ok29Qk_589ksELAT90jYHXEJinwUYew4eK2_KZ_V6P5IXc_RFR7thTueyHjkYCTiFrThPlOBzMvB6VzUnfSjbpYmlaNMFPLZanrS3waJCzJYAHneDBXHSVR2XhJRGnntt-Sl7SYxXL4znTpTNY26KtUREvz7KEIM4kv8GavE0hHAHjGtNVg9Mg-TS2hfqtdJbF0GHEdjkdCqiI9WfqaTyuGWg-AIEsehStJkUcUsGYIE_YIEHx8CfFoyTjs8hNiWDAdjBA-ZsnSbO1hUyg1usJsO5heBZpL50gTSNpUBVpRmT7pswTpDUzL2D4MX_yuQeDeLxBu5hg
```

3) Attempt to use the refresh token get a new access token.

```
curl --user "$CLIENT_ID:$CLIENT_SECRET" \
  -X POST http://localhost:8090/oauth/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=refresh_token&scope=write&scope=read&refresh_token=$REFRESH_TOKEN"
```

The following error is received:

``` 
2021-08-19 10:57:44.153 DEBUG 27885 --- [nio-8090-exec-5] o.s.security.web.FilterChainProxy        : Securing POST /oauth/token
2021-08-19 10:57:44.154 DEBUG 27885 --- [nio-8090-exec-5] s.s.w.c.SecurityContextPersistenceFilter : Set SecurityContextHolder to empty SecurityContext
2021-08-19 10:57:44.304 DEBUG 27885 --- [nio-8090-exec-5] o.s.s.a.dao.DaoAuthenticationProvider    : Authenticated user
2021-08-19 10:57:44.304 DEBUG 27885 --- [nio-8090-exec-5] o.s.s.w.a.www.BasicAuthenticationFilter  : Set SecurityContextHolder to UsernamePasswordAuthenticationToken [Principal=org.springframework.security.core.userdetails.User [Username=gateway-client, Password=[PROTECTED], Enabled=true, AccountNonExpired=true, credentialsNonExpired=true, AccountNonLocked=true, Granted Authorities=[]], Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null], Granted Authorities=[]]
2021-08-19 10:57:44.304 DEBUG 27885 --- [nio-8090-exec-5] o.s.s.w.a.i.FilterSecurityInterceptor    : Authorized filter invocation [POST /oauth/token] with attributes [fullyAuthenticated]
2021-08-19 10:57:44.304 DEBUG 27885 --- [nio-8090-exec-5] o.s.security.web.FilterChainProxy        : Secured POST /oauth/token
2021-08-19 10:57:44.304 DEBUG 27885 --- [nio-8090-exec-5] o.s.web.servlet.DispatcherServlet        : POST "/oauth/token", parameters={masked}
2021-08-19 10:57:44.304 DEBUG 27885 --- [nio-8090-exec-5] .s.o.p.e.FrameworkEndpointHandlerMapping : Mapped to org.springframework.security.oauth2.provider.endpoint.TokenEndpoint#postAccessToken(Principal, Map)
2021-08-19 10:57:44.511 DEBUG 27885 --- [nio-8090-exec-5] o.s.s.o.p.refresh.RefreshTokenGranter    : Getting access token for: gateway-client
2021-08-19 10:57:44.513 DEBUG 27885 --- [nio-8090-exec-5] p.PreAuthenticatedAuthenticationProvider : PreAuthenticated authentication request: PreAuthenticatedAuthenticationToken [Principal=UsernamePasswordAuthenticationToken [Principal=john, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_USER, ROLE_EMPLOYEE]], Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_USER, ROLE_EMPLOYEE]]
2021-08-19 10:57:44.513 DEBUG 27885 --- [nio-8090-exec-5] .m.m.a.ExceptionHandlerExceptionResolver : Using @ExceptionHandler org.springframework.security.oauth2.provider.endpoint.TokenEndpoint#handleException(Exception)
2021-08-19 10:57:44.514 ERROR 27885 --- [nio-8090-exec-5] o.s.s.o.provider.endpoint.TokenEndpoint  : Handling error: IllegalStateException, UserDetailsService is required.

java.lang.IllegalStateException: UserDetailsService is required.
	at org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter$UserDetailsServiceDelegator.loadUserByUsername(WebSecurityConfigurerAdapter.java:470) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper.loadUserDetails(UserDetailsByNameServiceWrapper.java:71) ~[spring-security-core-5.5.0.jar:5.5.0]
	at org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider.authenticate(PreAuthenticatedAuthenticationProvider.java:97) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.authentication.ProviderManager.authenticate(ProviderManager.java:182) ~[spring-security-core-5.5.0.jar:5.5.0]
	at org.springframework.security.oauth2.provider.token.DefaultTokenServices.refreshAccessToken(DefaultTokenServices.java:150) ~[spring-security-oauth2-2.3.8.RELEASE.jar:na]
	at org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter.getAccessToken(RefreshTokenGranter.java:47) ~[spring-security-oauth2-2.3.8.RELEASE.jar:na]
	at org.springframework.security.oauth2.provider.token.AbstractTokenGranter.grant(AbstractTokenGranter.java:67) ~[spring-security-oauth2-2.3.8.RELEASE.jar:na]
	at org.springframework.security.oauth2.provider.CompositeTokenGranter.grant(CompositeTokenGranter.java:38) ~[spring-security-oauth2-2.3.8.RELEASE.jar:na]
	at org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer$4.grant(AuthorizationServerEndpointsConfigurer.java:583) ~[spring-security-oauth2-2.3.8.RELEASE.jar:na]
	at org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(TokenEndpoint.java:132) ~[spring-security-oauth2-2.3.8.RELEASE.jar:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:197) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:141) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:894) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1063) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:652) ~[tomcat-embed-core-9.0.46.jar:4.0.FR]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:733) ~[tomcat-embed-core-9.0.46.jar:4.0.FR]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.springframework.web.servlet.resource.ResourceUrlEncodingFilter.doFilter(ResourceUrlEncodingFilter.java:67) ~[spring-webmvc-5.3.8.jar:5.3.8]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:327) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:115) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:81) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:121) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:115) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:126) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:81) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:105) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:149) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.authentication.www.BasicAuthenticationFilter.doFilterInternal(BasicAuthenticationFilter.java:178) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:103) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:89) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:110) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:80) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:55) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:211) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:183) ~[spring-security-web-5.5.0.jar:5.5.0]
	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:358) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:271) ~[spring-web-5.3.8.jar:5.3.8]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.3.8.jar:5.3.8]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.3.8.jar:5.3.8]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:96) ~[spring-boot-actuator-2.5.1.jar:2.5.1]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.3.8.jar:5.3.8]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-5.3.8.jar:5.3.8]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.3.8.jar:5.3.8]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at com.example.demo.springsecurity.config.CorsFilter.doFilter(CorsFilter.java:27) ~[classes/:na]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:143) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:357) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:374) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:893) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1707) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628) ~[na:na]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.46.jar:9.0.46]
	at java.base/java.lang.Thread.run(Thread.java:834) ~[na:na]

2021-08-19 10:57:44.515 DEBUG 27885 --- [nio-8090-exec-5] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Using 'application/json', given [*/*] and supported [application/json, application/*+json, application/json, application/*+json]
2021-08-19 10:57:44.515 DEBUG 27885 --- [nio-8090-exec-5] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Writing [error="server_error", error_description="Internal Server Error"]
2021-08-19 10:57:44.515  WARN 27885 --- [nio-8090-exec-5] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [java.lang.IllegalStateException: UserDetailsService is required.]
2021-08-19 10:57:44.515 DEBUG 27885 --- [nio-8090-exec-5] o.s.web.servlet.DispatcherServlet        : Completed 500 INTERNAL_SERVER_ERROR
2021-08-19 10:57:44.515 DEBUG 27885 --- [nio-8090-exec-5] s.s.w.c.SecurityContextPersistenceFilter : Cleared SecurityContextHolder to complete request
``` 
