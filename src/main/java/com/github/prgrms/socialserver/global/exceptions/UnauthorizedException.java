package com.github.prgrms.socialserver.global.exceptions;

import com.github.prgrms.socialserver.global.utils.MessageUtil;

public class UnauthorizedException extends ServiceRuntimeException {

    public static final String MESSAGE_KEY = "error.auth";

    public static final String MESSAGE_DETAIL = "error.auth.details";

    public UnauthorizedException(String message) {
        super(MESSAGE_KEY, MESSAGE_DETAIL, new Object[] {message});
    }

    public String getMessageDetail() {
        return MessageUtil.getMessage(getDetailKey(), getParams());
    }

    @Override
    public String getMessage() {
        return MessageUtil.getMessage(getMessageKey());
    }

}
