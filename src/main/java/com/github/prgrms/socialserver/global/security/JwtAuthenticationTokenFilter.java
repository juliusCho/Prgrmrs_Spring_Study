package com.github.prgrms.socialserver.global.security;

import com.github.prgrms.socialserver.users.model.EmailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String headerKey;
    private final Jwt jwt;

    public JwtAuthenticationTokenFilter(String headerKey, Jwt jwt) {
        this.headerKey = headerKey;
        this.jwt = jwt;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authorizationToken = this.obtainAuthorizationToken(request);

            if (authorizationToken != null) {
                try {
                    Jwt.Claims claims = this.verify(authorizationToken);
                    log.debug("Jwt parse result: {}", claims);

                    // 만료 10분 전
                    if (this.canRefresh(claims, 60000 * 10)) {
                        String refreshToken = jwt.refreshToken(authorizationToken);
                        response.setHeader(headerKey, refreshToken);
                    }

                    Long userKey = claims.userKey;
                    EmailVO email = claims.email;
                    // TODO 이름을 JWT 토큰에서 가져와보자

                    List<GrantedAuthority> authorities = obtainAuthorities(claims);

                    if (userKey != null && email != null && authorities.size() > 0) {
                        JwtAuthenticationTokenVO authenticationTokenVO =
                                new JwtAuthenticationTokenVO(new JwtAuthenticationVO(userKey, email), null, authorities);
                        authenticationTokenVO.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationTokenVO);
                    }
                } catch (Exception e) {
                    log.warn("Jwt processing failed: {}", e.getMessage());
                }
            }
        } else {
            log.debug(
                    "SecurityContextHolder not populated with security token, since it has already been contained: {}",
                    SecurityContextHolder.getContext().getAuthentication()
                    );
        }
        filterChain.doFilter(request, response);
    }

    private String obtainAuthorizationToken(HttpServletRequest request) {
        String token = request.getHeader(headerKey);
        if (token == null) return null;

        if (log.isDebugEnabled()) log.debug("Jwt authorization api detected: {}", token);
        try {
            token = URLDecoder.decode(token, "UTF-8");
            String[] parts = token.split(" ");
            if (parts.length != 2) return null;

            String scheme = parts[0];
            String credentials = parts[1];
            return BEARER.matcher(scheme).matches() ? credentials : null;

        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private Jwt.Claims verify(String token) {
        return jwt.verify(token);
    }

    private boolean canRefresh(Jwt.Claims claims, long refreshRangeMillis) {
        long exp = claims.exp();
        if (exp <= 0) return false;

        long remain = exp - System.currentTimeMillis();
        return remain < refreshRangeMillis;
    }

    private List<GrantedAuthority> obtainAuthorities(Jwt.Claims claims) {
        String[] roles = claims.roles;
        return roles == null || roles.length == 0 ?
                Collections.emptyList() :
                Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(toList());
    }

}
