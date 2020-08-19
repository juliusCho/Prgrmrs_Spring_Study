package com.github.prgrms.socialserver.users.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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

    private final String email;

    private final String passwd;

    private Integer loginCount;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createAt;


    public static class Builder {
        private final Long seq;
        private final String email;
        private final String passwd;
        private Integer loginCount;
        private LocalDateTime lastLoginAt;
        private LocalDateTime createAt;

        public Builder(Long seq) {
            this.seq = seq;
            this.email = "";
            this.passwd = "";
        }
        public Builder(String email, String passwd) {
            this.seq = 0L;
            this.email = email;
            this.passwd = passwd;
        }
        public Builder(Long seq, String email, String passwd) {
            this.seq = seq;
            this.email = email;
            this.passwd = passwd;
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

    public String getEmail() {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final UserEntity entity = (UserEntity) o;
        return this.email.equals(entity.email) &&
                this.passwd.equals(entity.passwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email, this.passwd);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "seq=" + seq +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", loginCount=" + loginCount +
                ", lastLoginAt=" + lastLoginAt +
                ", createAt=" + createAt +
                '}';
    }

}
