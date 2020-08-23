package com.github.prgrms.socialserver.global.web;

import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.global.utils.MessageUtil;
import org.json.JSONException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalController {

    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO> duplicatedUser(IllegalArgumentException e) {
        return ResponseEntity.ok(new ApiResponseDTO(false, e.getMessage()));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponseDTO> emptyResult() {
        return ResponseEntity.ok(new ApiResponseDTO(true, MessageUtil.getMessage("api.users.msg.norecord")));
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<ApiResponseDTO> invalidRequest() {
        return ResponseEntity.ok(new ApiResponseDTO(false, MessageUtil.getMessage("api.users.msg.invalid_req")));
    }

}
