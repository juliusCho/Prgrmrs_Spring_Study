package com.github.prgrms.socialserver.users.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JoinRequestDTO {

  // TODO 이름 프로퍼티 추가

  private String principal;

  private String credentials;

  protected JoinRequestDTO() {}

  public String getPrincipal() {
    return principal;
  }

  public String getCredentials() {
    return credentials;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("principal", principal)
      .append("credentials", credentials)
      .toString();
  }

}