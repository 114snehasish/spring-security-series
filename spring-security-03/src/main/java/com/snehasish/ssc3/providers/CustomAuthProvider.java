package com.snehasish.ssc3.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

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
        return false;
    }
}
