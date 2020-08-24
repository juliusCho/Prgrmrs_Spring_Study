package com.github.prgrms.socialserver.users.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkNotNull;

public class JoinResultDTO {

  private final String apiToken;

  private final UserDTO user;

  public JoinResultDTO(String apiToken, UserDTO user) {
    checkNotNull(apiToken, "apiToken must be provided.");
    checkNotNull(user, "user must be provided.");

    this.apiToken = apiToken;
    this.user = user;
  }

  public String getApiToken() {
    return apiToken;
  }

  public UserDTO getUser() {
    return user;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("apiToken", apiToken)
      .append("user", user)
      .toString();
  }

}