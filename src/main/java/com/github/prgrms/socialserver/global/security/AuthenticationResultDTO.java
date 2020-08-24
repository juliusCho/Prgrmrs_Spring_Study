package com.github.prgrms.socialserver.global.security;

import com.github.prgrms.socialserver.users.model.UserDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AuthenticationResultDTO {

  private String apiToken;

  private UserDTO user;

  public AuthenticationResultDTO(AuthenticationResultVO source) {
    copyProperties(source, this);

    this.user = new UserDTO(source.getUserEntity());
  }

  public String getApiToken() {
    return apiToken;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("apiToken", apiToken)
      .append("user", user)
      .toString();
  }

}