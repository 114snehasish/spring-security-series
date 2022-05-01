package com.snehasish.springsecurity04.providers;

import com.snehasish.springsecurity04.services.DBUserDetailsManager;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public record CustomAuthProvider(
        DBUserDetailsManager userDetailsManager,
        PasswordEncoder passwordEncoder
) implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        UserDetails user = null;
        log.debug("Trying to authenticate {}", userName);
        try {
            user = userDetailsManager.loadUserByUsername(userName);
        } catch (UsernameNotFoundException ex) {
            log.error("user with name {} not found. Error thrown with message: {}", userName, ex.getMessage());
            throw ex;
        }
        log.debug("User found. checking password");
        if (passwordEncoder.matches(password, user.getPassword())) {
            log.debug("User authenticated. Returning fully authenticated object");
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        } else {
            log.error("Password did not match. {} will be thrown",
                    BadCredentialsException.class.getSimpleName());
            throw new BadCredentialsException("Error, password did not match");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.debug("Checking whether {} support {} or not",
                getClass().getSimpleName(),
                authentication.getSimpleName());
        val result = UsernamePasswordAuthenticationToken.class.equals(authentication);
        log.debug("The authentication type is " + (result ? "supported" : "not supported"));
        return result;
    }
}
