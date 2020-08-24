package com.github.prgrms.socialserver.global.security;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.users.model.EmailVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkNotNull;

public class JwtAuthenticationVO {

    public final IdVO<UserEntity, Long> id;
    public final EmailVO email;

    // TODO 이름 프로퍼티 추가

    JwtAuthenticationVO(Long id, EmailVO email) {
        checkNotNull(id, "id must be provided");
        checkNotNull(email, "email must be provided");

        this.id = IdVO.of(UserEntity.class, id);
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("email", email)
                .toString();
    }

}
