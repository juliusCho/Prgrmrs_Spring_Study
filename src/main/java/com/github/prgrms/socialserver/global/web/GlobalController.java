package com.github.prgrms.socialserver.global.web;

import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class GlobalController {

    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";

    @Qualifier("messageSource")
    @Autowired
    public MessageSource messageSource;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO> duplicatedUser() {
        return ResponseEntity.ok(new ApiResponseDTO(false, messageSource.getMessage("api.users.msg.duplicate", null, Locale.getDefault())));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponseDTO> emptyResult() {
        return ResponseEntity.ok(new ApiResponseDTO(true, messageSource.getMessage("api.users.msg.norecord", null, Locale.getDefault())));
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<ApiResponseDTO> invalidRequest() {
        return ResponseEntity.ok(new ApiResponseDTO(false, messageSource.getMessage("api.users.msg.invalid_req", null, Locale.getDefault())));
    }

}
