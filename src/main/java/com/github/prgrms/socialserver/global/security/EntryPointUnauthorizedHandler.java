package com.github.prgrms.socialserver.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    static ApiResponseDTO<?> E401 = new ApiResponseDTO<>("Authentication error (cause: unauthorized)", HttpStatus.UNAUTHORIZED);

    private final ObjectMapper om;

    public EntryPointUnauthorizedHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setHeader("content-type", "application/json");
        httpServletResponse.getWriter().write(om.writeValueAsString(E401));
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }

}
