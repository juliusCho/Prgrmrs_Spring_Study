package com.github.prgrms.socialserver.global.model;

public class ApiResponseDTO<T> {

    private final boolean success;

    private final T response;

    public ApiResponseDTO(boolean success, T response) {
        this.success = success;
        this.response = response;
    }


    public boolean isSuccess() {
        return this.success;
    }


    @Override
    public String toString() {
        return "ApiResponseDTO{" +
                "success=" + success +
                ", response='" + response + '\'' +
                '}';
    }

}
