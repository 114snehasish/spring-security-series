package com.snehasish.ssc3.providers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public record CustomAuthProvider(
        UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder
) implements AuthenticationProvider {

    /**
     * This is called by Authentication Manager once it determines this provider can support
     * the type of authentication in hand. This method carries out the actual authentication process
     * by taking help form UserDetailsService and PasswordEncoder.
     *
     * @param authentication The instance of authentication object that needs to be validated.
     * @return Fully authenticated object where the isAuthenticate is set to true and the authorities are populated. It returns null if this instance of authentication is not supported.
     * @throws AuthenticationException in case of the authentication instance is invalid.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        UserDetails user = null;
        try {
            user = userDetailsService.loadUserByUsername(userName);
        } catch (UsernameNotFoundException ex) {
            log.error("user with name {} not found. Error thrown with message: {}", userName, ex.getMessage());
            throw ex;
        }
        if (Objects.nonNull(user)) {
            log.debug("User found. checking password");
            if (passwordEncoder.matches(password, user.getPassword())) {
                log.debug("User authenticated. Returning fully authenticated object");
                return new UsernamePasswordAuthenticationToken(userName, password, user.getAuthorities());
            } else {
                log.error("Password did not match. {} will be thrown",
                        BadCredentialsException.class.getSimpleName());
                throw new BadCredentialsException("Error, password did not match");
            }
        }
        return null;
    }

    /**
     * By This method the authentication manager decides whether a particular authentication type
     * can be validated by this provider.
     *
     * @param authentication The type of authentication that the authentication manager wants to validate.
     * @return true or false based on whether the provider supports the received authentication.
     */
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
