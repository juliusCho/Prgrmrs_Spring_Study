package com.github.prgrms.socialserver.global.security;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AuthenticationRequestVO {

    private String principal;
    private String credentials;

    protected AuthenticationRequestVO() {}

    public AuthenticationRequestVO(String principal, String credentials) {
        this.principal = principal;
        this.credentials = credentials;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public String getCredentials() {
        return this.credentials;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("principal", principal)
                .append("credentials", credentials)
                .toString();
    }

}
