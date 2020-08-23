package com.github.prgrms.socialserver.global.exceptions;

import com.github.prgrms.socialserver.global.utils.MessageUtil;
import org.apache.commons.lang3.StringUtils;

public class NotFoundException extends ServiceRuntimeException {

    static final String MESSAGE_KEY = "error.notfound";

    static final String MESSAGE_DETAILS = "error.notfound.details";

    public NotFoundException(String targetName, Object... values) {
        super(MESSAGE_KEY, MESSAGE_DETAILS, new String[] {values != null && values.length > 0 ? StringUtils.join(values, ",") : ""});
    }

    public NotFoundException(Class clazz, Object... values) {
        this(clazz.getSimpleName(), values);
    }

    public String getMessageDetail() {
        return MessageUtil.getMessage(getDetailKey(), getParams());
    }

    @Override
    public String getMessage() {
        return MessageUtil.getMessage(getMessageKey());
    }

}
