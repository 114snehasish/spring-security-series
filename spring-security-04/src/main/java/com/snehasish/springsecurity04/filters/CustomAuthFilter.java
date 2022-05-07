package com.snehasish.springsecurity04.filters;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthFilter implements Filter {
    public static final String HEADER_USERNAME = "username";
    public static final String HEADER_PASSWORD = "password";
    private AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        val username = getHeaderValue(servletRequest, HEADER_USERNAME);
        val password = getHeaderValue(servletRequest, HEADER_PASSWORD);
        try {
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
                throw new BadCredentialsException("Username or password cannot be empty");
            val authentication = new UsernamePasswordAuthenticationToken(username, password);
            val fullAuthentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(fullAuthentication);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthenticationException ex) {
            log.error("Exception occurred while authenticating: {}", ex.getMessage());
        }
    }

    private String getHeaderValue(ServletRequest servletRequest, String headerName) {
        return ((HttpServletRequest) servletRequest).getHeader(headerName);
    }

    @Autowired
    @Lazy
    public void setAuthenticationManager(AuthenticationManager manager) {
        this.authenticationManager = manager;
    }

}
