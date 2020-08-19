package com.github.prgrms.socialserver.users.model;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -1322458234837696034L;


    public UserDTO(String passwd, String email) {
        this.passwd = passwd;
        this.email = email;
    }

    private UserDTO(Builder builder) {
        email = builder.email;
        passwd = builder.passwd;
        loginCount = builder.loginCount;
        lastLoginAt = builder.lastLoginAt;
        createAt = builder.createAt;
    }


    private final String email;
    private final String passwd;
    private int loginCount;
    private String lastLoginAt;
    private String createAt;


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
        private final String email;
        private final String passwd;
        private int loginCount;
        private String lastLoginAt;
        private String createAt;

        public Builder(String email, String passwd) {
            this.email = email;
            this.passwd = passwd;
        }
        public Builder loginCount(int loginCount) {
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
        return this.email.equals(userDTO.email) &&
                this.passwd.equals(userDTO.passwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email, this.passwd);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", loginCount=" + loginCount +
                ", lastLoginAt='" + lastLoginAt + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }

}
