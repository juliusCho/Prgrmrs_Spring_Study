package com.github.prgrms.socialserver.global.security;

import com.github.prgrms.socialserver.global.exceptions.NotFoundException;
import com.github.prgrms.socialserver.global.types.Role;
import com.github.prgrms.socialserver.users.model.EmailVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;
    private final UserService userService;

    public JwtAuthenticationProvider(Jwt jwt, UserService userService) {
        this.jwt = jwt;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationTokenVO authenticationTokenVO = (JwtAuthenticationTokenVO) authentication;
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return isAssignable(JwtAuthenticationTokenVO.class, aClass);
    }

    private Authentication processUserAuthentication(AuthenticationRequestVO requestVO) {
        try {
            UserEntity user = userService.login(new EmailVO(requestVO.getPrincipal()), requestVO.getCredentials());
            JwtAuthenticationTokenVO jwtAuthenticationTokenVO =
                    new JwtAuthenticationTokenVO(new JwtAuthenticationVO(user.getSeq(), user.getEmail()), null, createAuthorityList(Role.USER.value()));
            String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
            jwtAuthenticationTokenVO.setDetails(new AuthenticationResultVO(apiToken, user));
            return jwtAuthenticationTokenVO;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

}
