package com.github.prgrms.socialserver.global.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

public class ApiResponseDTO<T> {

    private final boolean success;

    private final T response;

    private final int status;

    public ApiResponseDTO(boolean success, T response) {
        this.success = success;
        this.response = response;
        this.status = HttpStatus.OK.value();
    }

    public ApiResponseDTO(T response, HttpStatus status) {
        this.success = false;
        this.response = response;
        this.status = status.value();
    }


    public boolean isSuccess() {
        return this.success;
    }

    public T getResponse() {
        return this.response;
    }

    public int getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("success", success)
                .append("response", response)
                .append("status", status)
                .toString();
    }

}
