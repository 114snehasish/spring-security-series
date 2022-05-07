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
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthFilter extends OncePerRequestFilter {
    public static final String HEADER_USERNAME = "username";
    public static final String HEADER_PASSWORD = "password";
    private AuthenticationManager authenticationManager;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws IOException, ServletException {
        val username = request.getHeader(HEADER_USERNAME);
        val password = request.getHeader(HEADER_PASSWORD);
        try {
            if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password))
                throw new BadCredentialsException("Username or password cannot be empty");
            val authentication = new UsernamePasswordAuthenticationToken(username, password);
            val fullAuthentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(fullAuthentication);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ex) {
            log.error("Exception occurred while authenticating: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Autowired
    @Lazy
    public void setAuthenticationManager(AuthenticationManager manager) {
        this.authenticationManager = manager;
    }

}
