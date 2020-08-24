package com.github.prgrms.socialserver.users.model;

import com.github.prgrms.socialserver.global.utils.DateUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ConnectedUserDTO {

  private Long seq;

  private EmailVO email;

  // TODO 이름 프로퍼티 추가

  private LocalDateTime grantedAt;
  private String grantedDt;

  public ConnectedUserDTO(ConnectedUserEntity source) {
    copyProperties(source, this);
  }

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public EmailVO getEmail() {
    return email;
  }

  public void setEmail(EmailVO email) {
    this.email = email;
  }

  public LocalDateTime getGrantedAt() {
    return DateUtil.convertToUTCDate(grantedDt);
  }

  public void setGrantedAt(LocalDateTime grantedAt) {
    this.grantedAt = grantedAt;
  }

  public String getGrantedDt() {
    return this.grantedDt;
  }

  public void setGrantedDt(final String grantedDt) {
    this.grantedDt = grantedDt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("email", email)
      .append("grantedAt", grantedAt)
      .append("grantedDt", grantedDt)
      .toString();
  }

}