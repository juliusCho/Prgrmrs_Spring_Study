package com.github.prgrms.socialserver.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    static ApiResponseDTO<?> E403 = new ApiResponseDTO<>("Authentication error (cause: forbidden)", HttpStatus.FORBIDDEN);

    private final ObjectMapper om;

    public JwtAccessDeniedHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setHeader("content-type", "application/json");
        httpServletResponse.getWriter().write(om.writeValueAsString(E403));
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }

}
