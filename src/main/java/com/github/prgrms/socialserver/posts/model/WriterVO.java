package com.github.prgrms.socialserver.posts.model;

import com.github.prgrms.socialserver.users.model.EmailVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;

public class WriterVO {

  private final EmailVO email;

  private final String name;

  public WriterVO(EmailVO email) {
    this(email, null);
  }

  public WriterVO(EmailVO email, String name) {
    checkNotNull(email, "email must be provided.");

    this.email = email;
    this.name = name;
  }

  public EmailVO getEmail() {
    return email;
  }

  public Optional<String> getName() {
    return ofNullable(name);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("email", email)
      .append("name", name)
      .toString();
  }

}