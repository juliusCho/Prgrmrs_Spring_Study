package com.github.prgrms.socialserver.global.security;

import com.github.prgrms.socialserver.users.model.UserEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkNotNull;

public class AuthenticationResultVO {

    private final String apiToken;
    private final UserEntity userEntity;

    public AuthenticationResultVO(String apiToken, UserEntity userEntity) {
        checkNotNull(apiToken, "apiToken must be provided");;
        checkNotNull(userEntity, "user must be provided");

        this.apiToken = apiToken;
        this.userEntity = userEntity;
    }

    public String getApiToken() {
        return this.apiToken;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("apiToken", apiToken)
                .append("userEntity", userEntity)
                .toString();
    }

}
