package com.github.prgrms.socialserver.global.configs;

import com.github.prgrms.socialserver.global.security.EntryPointUnauthorizedHandler;
import com.github.prgrms.socialserver.global.security.Jwt;
import com.github.prgrms.socialserver.global.security.JwtAccessDeniedHandler;
import com.github.prgrms.socialserver.global.security.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Jwt jwt;
    private final JwtTokenConfigure jwtTokenConfigure;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    public WebSecurityConfig(Jwt jwt, JwtTokenConfigure jwtTokenConfigure, JwtAccessDeniedHandler jwtAccessDeniedHandler, EntryPointUnauthorizedHandler entryPointUnauthorizedHandler) {
        this.jwt = jwt;
        this.jwtTokenConfigure = jwtTokenConfigure;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.entryPointUnauthorizedHandler = entryPointUnauthorizedHandler;
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(jwtTokenConfigure.getHeader(), jwt);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
