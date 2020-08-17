package com.github.prgrms.socialserver.users.model;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -1322458234837696034L;


    public UserDTO() {}

    public UserDTO(String passwd, String email) {
        seq = 0L;
        this.passwd = passwd;
        this.email = email;
    }

    private UserDTO(Builder builder) {
        seq = builder.seq;
        email = builder.email;
        passwd = builder.passwd;
        loginCount = builder.loginCount;
        lastLoginAt = builder.lastLoginAt;
        createAt = builder.createAt;
    }




    private Long seq;
    private String email;
    private String passwd;
    private int loginCount;
    private String lastLoginAt;
    private String createAt;




    public Long getSeq() {
        return this.seq;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPasswd() {
        return this.passwd;
    }
    public int getLoginCount() {
        return this.loginCount;
    }
    public String getLastLoginAt() {
        return this.lastLoginAt;
    }
    public String getCreateAt() {
        return this.createAt;
    }




    public static class Builder {
        private final Long seq;
        private final String email;
        private String passwd;
        private int loginCount;
        private String lastLoginAt;
        private String createAt;

        public Builder(Long seq, String email) {
            this.seq = seq;
            this.email = email;
        }

        public Builder passwd(String passwd) {
            this.passwd = passwd;
            return this;
        }
        public Builder loginCount(int passwd) {
            this.loginCount = loginCount;
            return this;
        }
        public Builder lastLoginAt(String lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
            return this;
        }
        public Builder createAt(String createAt) {
            this.createAt = createAt;
            return this;
        }
        public UserDTO build() {
            return new UserDTO(this);
        }
    }





    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final UserDTO userDTO = (UserDTO) o;
        return this.seq.equals(userDTO.seq) &&
                this.email.equals(userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.seq, this.email);
    }




    @Override
    public String toString() {
        return "UserDTO{" +
                "seq=" + seq +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", loginCount=" + loginCount +
                ", lastLoginAt='" + lastLoginAt + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }

}
