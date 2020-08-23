package com.github.prgrms.socialserver.global.exceptions;

public abstract class ServiceRuntimeException extends RuntimeException {

    private String messageKey;
    private String detailKey;
    private Object[] params;

    public ServiceRuntimeException(String messageKey, String detailKey, Object[] params) {
        this.messageKey = messageKey;
        this.detailKey = detailKey;
        this.params = params;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public String getDetailKey() {
        return this.detailKey;
    }

    public Object[] getParams() {
        return this.params;
    }

}
