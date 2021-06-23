package com.example.gateway.config;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TrustedRestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {

  @Value("${authorization-server-url:HOST_NOT_SET}")
  private String host;

  @Value("${resource-client-id:USERNAME_NOT_SET}")
  private String username;

  @Value("${resource-client-secret:PASSWORD_NOT_SET}")
  private String password;

  private RestTemplate restTemplate;

  @Override
  public RestTemplate getObject() {
    return restTemplate;
  }

  @Override
  public Class<RestTemplate> getObjectType() {
    return RestTemplate.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  @Override
  public void afterPropertiesSet() {
    HttpHost httpHost = new HttpHost(this.host, 443, "https");
    final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost);
    restTemplate = new RestTemplate(requestFactory);
    restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username, password));
  }
}
