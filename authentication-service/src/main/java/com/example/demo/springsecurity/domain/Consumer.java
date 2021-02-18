package com.example.demo.springsecurity.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import java.io.Serializable;
import java.util.*;

@Data
public class Consumer implements ClientDetails, Serializable {


    private String clientId;

    private String resourceIdsCsv;

    private String clientSecret;

    private String scopeCsv;

    private String authorizedGrantTypesCsv;

    private String registeredRedirectUrisCsv;

    private String authorityCsv;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private String additionalInfo;

    private String autoApproveCsv;

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if (authorizedGrantTypesCsv != null) {
            return new HashSet<>(Arrays.asList(authorizedGrantTypesCsv.split(",")));
        }
        return Collections.emptySet();
    }

    @Override
    public Set<String> getResourceIds() {
        if (resourceIdsCsv != null) {
            return new HashSet<>(Arrays.asList(resourceIdsCsv.split(",")));
        }
        return Collections.emptySet();
    }

    @Override
    public Set<String> getScope() {
        if (scopeCsv != null) {
            return new HashSet<>(Arrays.asList(scopeCsv.split(",")));
        }
        return Collections.emptySet();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if (registeredRedirectUrisCsv != null) {
            return new HashSet<>(Arrays.asList(registeredRedirectUrisCsv.split(",")));
        }
        return Collections.emptySet();
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public boolean isScoped() {
        return this.scopeCsv != null && !Arrays.asList(scopeCsv.split(",")).isEmpty();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (autoApproveCsv == null) {
            return false;
        }
        for (String auto : autoApproveCsv.split(",")) {
            if ("true".equals(auto) || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.emptyMap();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (authorityCsv != null) {
            Set<GrantedAuthority> grantedAuthorityList = new HashSet<>();
            for (String authority : authorityCsv.split(",")) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(authority));
            }
            return grantedAuthorityList;
        }
        return Collections.emptySet();
    }
}
