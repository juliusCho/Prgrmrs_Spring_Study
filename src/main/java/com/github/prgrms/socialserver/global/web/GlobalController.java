package com.github.prgrms.socialserver.global.web;

import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Locale;

@ControllerAdvice
public class GlobalController {

    @Qualifier("messageSource")
    @Autowired
    MessageSource msg;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO> duplicatedUser() {
        return ResponseEntity.ok(new ApiResponseDTO(false, msg.getMessage("api.users.msg.duplicate", null, Locale.KOREA)));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiResponseDTO> emptyResult() {
        return ResponseEntity.ok(new ApiResponseDTO(true, msg.getMessage("api.users.msg.norecord", null, Locale.KOREA)));
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<ApiResponseDTO> invalidRequest() {
        return ResponseEntity.ok(new ApiResponseDTO(false, msg.getMessage("api.users.msg.invalid_req", null, Locale.KOREA)));
    }

}
