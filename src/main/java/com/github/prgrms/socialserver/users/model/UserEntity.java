package com.github.prgrms.socialserver.users.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UserEntity implements Serializable {

    private static final long serialVersionUID = -7896294193836119831L;




    private Long seq = 0L;

    private String email = "";

    private String passwd;

    private int loginCount;

    private Date lastLoginAt;

    private Date createAt;





    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getLoginCount() {
        return this.loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoginAt() {
        return this.lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }






    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final UserEntity entity = (UserEntity) o;
        return this.loginCount == entity.loginCount &&
                Objects.equals(this.seq, entity.seq) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.passwd, entity.passwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.seq, this.email, this.passwd);
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
