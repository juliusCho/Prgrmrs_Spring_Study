package com.github.prgrms.socialserver.global.model;

import java.io.Serializable;

public class ApiResponseDTO implements Serializable {

    private static final long serialVersionUID = -5065359100290454048L;

    private boolean success;

    private String response;

    public ApiResponseDTO() {};

    public ApiResponseDTO(boolean success, String response) {
        this.success = success;
        this.response = response;
    }


    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(final String response) {
        this.response = response;
    }


    @Override
    public String toString() {
        return "ApiResponseDTO{" +
                "success=" + success +
                ", response='" + response + '\'' +
                '}';
    }

}
