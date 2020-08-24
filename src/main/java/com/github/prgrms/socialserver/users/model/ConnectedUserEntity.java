package com.github.prgrms.socialserver.users.model;

import com.github.prgrms.socialserver.global.utils.DateUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

public class ConnectedUserEntity {

  private final Long seq;

  private final EmailVO email;

  // TODO 이름 프로퍼티 추가

  private final LocalDateTime grantedAt;
  private String grantedDt;

  public ConnectedUserEntity(Long seq, EmailVO email, LocalDateTime grantedAt) {
    checkNotNull(seq, "seq must be provided.");
    checkNotNull(email, "email must be provided.");
    checkNotNull(grantedAt, "grantedAt must be provided.");

    this.seq = seq;
    this.email = email;
    this.grantedAt = grantedAt;
  }

  public Long getSeq() {
    return seq;
  }

  public EmailVO getEmail() {
    return email;
  }

  public LocalDateTime getGrantedAt() {
    return grantedAt;
  }

  public String getGrantedDt() {
    return DateUtil.convertToLocalString(grantedAt);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("email", email)
      .append("grantedAt", grantedAt)
      .toString();
  }

}