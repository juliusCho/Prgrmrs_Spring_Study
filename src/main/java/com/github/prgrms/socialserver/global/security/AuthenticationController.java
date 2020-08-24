package com.github.prgrms.socialserver.global.security;

import com.github.prgrms.socialserver.global.exceptions.UnauthorizedException;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ApiResponseDTO<AuthenticationResultDTO> authentication(
            @RequestBody AuthenticationRequestVO authenticationRequestVO
    ) throws UnauthorizedException {
        try {
            JwtAuthenticationTokenVO tokenVO = new JwtAuthenticationTokenVO(authenticationRequestVO.getPrincipal(), authenticationRequestVO.getCredentials());
            Authentication authentication = authenticationManager.authenticate(tokenVO);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ApiResponseDTO(
                    true,
                    new AuthenticationResultDTO((AuthenticationResultVO) authentication.getDetails())
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

}
