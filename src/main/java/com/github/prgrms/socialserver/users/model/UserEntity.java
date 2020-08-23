package com.github.prgrms.socialserver.users.model;

import com.github.prgrms.socialserver.global.security.Jwt;
import com.github.prgrms.socialserver.global.utils.DateUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.LocalDateTime.now;

public class UserEntity implements Serializable {

    private static final long serialVersionUID = -7896294193836119831L;



    public UserEntity(Builder builder) {
        seq = builder.seq;
        email = builder.email;
        passwd = builder.passwd;
        loginCount = builder.loginCount;
        lastLoginAt = builder.lastLoginAt;
        createAt = builder.createAt;
    }





    private final Long seq;

    private final EmailVO email;

    private final String passwd;

    private Integer loginCount;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createAt;

    private String lastLoginDt;

    private String createDt;






    public static class Builder {
        private Long seq;
        private EmailVO email;
        private String passwd;
        private Integer loginCount;
        private LocalDateTime lastLoginAt;
        private LocalDateTime createAt;

        public Builder(Long seq) {
            this.seq = seq;
            this.email = null;
            this.passwd = "";
        }
        public Builder(String email, String passwd) {
            this.seq = 0L;
            this.email = new EmailVO(email);
            this.passwd = passwd;
        }
        public Builder(Long seq, String email, String passwd) {
            this.seq = seq;
            this.email = new EmailVO(email);
            this.passwd = passwd;
        }
        public Builder(String email) {
            this.seq = 0L;
            this.email = new EmailVO(email);
            this.passwd = "";
        }
        public Builder loginCount(Integer loginCount) {
            this.loginCount = loginCount;
            return this;
        }
        public Builder lastLoginAt(LocalDateTime lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
            return this;
        }
        public Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }
        public UserEntity build() {
            return new UserEntity(this);
        }
    }






    public Long getSeq() {
        return this.seq;
    }

    public EmailVO getEmail() {
        return this.email;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public Integer getLoginCount() {
        return this.loginCount;
    }

    public LocalDateTime getLastLoginAt() {
        return this.lastLoginAt;
    }

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    public String getLastLoginDt() {
        return DateUtil.convertToLocalString(this.getLastLoginAt());
    }

    public String getCreateDt() {
        return DateUtil.convertToLocalString(this.getCreateAt());
    }




    public void afterLoginSuccess() {
        loginCount++;
        lastLoginAt = now();
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, passwd))
            throw new IllegalArgumentException("Bad credential");
    }

    public String newApiToken(Jwt jwt, String[] roles) {
        // TODO jwt 토큰에 이름 프로퍼티 추가
        Jwt.Claims claims = Jwt.Claims.of(seq, email, roles);
        return jwt.newToken(claims);
    }



    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final UserEntity entity = (UserEntity) o;
        return this.email.getAddress().equals(entity.email.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email.getAddress());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("email", email)
                .append("password", "[PROTECTED]")
                .append("loginCount", loginCount)
                .append("lastLoginAt", lastLoginAt)
                .append("createAt", createAt)
                .append("lastLoginDt", lastLoginDt)
                .append("createDt", createDt)
                .toString();
    }

}
