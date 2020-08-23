package com.github.prgrms.socialserver.users.model;

import com.github.prgrms.socialserver.global.utils.DateUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class UserDTO {

    public UserDTO(UserEntity entity) {
        copyProperties(entity, this);
    }

    private Long seq;

    private EmailVO email;

    // TODO 이름 프로퍼티 추가

    private int loginCount;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createAt;

    private String lastLoginDt;

    private String createDt;

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

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public LocalDateTime getLastLoginAt() {
        return DateUtil.convertToUTCDate(lastLoginDt);
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public LocalDateTime getCreateAt() {
        return DateUtil.convertToUTCDate(createDt);
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getLastLoginDt() {
        return this.lastLoginDt;
    }

    public void setLastLoginDt(final String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public String getCreateDt() {
        return this.createDt;
    }

    public void setCreateDt(final String createDt) {
        this.createDt = createDt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("email", email)
                .append("loginCount", loginCount)
                .append("lastLoginAt", lastLoginAt)
                .append("createAt", createAt)
                .append("lastLoginDt", lastLoginDt)
                .append("createDt", createDt)
                .toString();
    }

}