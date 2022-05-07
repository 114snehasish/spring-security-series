package com.snehasish.springsecurity04.services;

import com.snehasish.springsecurity04.dao.UserRepository;
import com.snehasish.springsecurity04.exceptions.UserCreationException;
import com.snehasish.springsecurity04.models.SecurityUser;
import com.snehasish.springsecurity04.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.IllegalFormatException;
import java.util.Optional;

@Slf4j
public record DBUserDetailsManager(
        UserRepository userRepository
) implements UserDetailsManager {
    private static final String DEFAULT_ERR_MSG = "Unable to perform database operation for user.";

    @Override
    public void createUser(UserDetails user) throws UserCreationException {
        try {
            userRepository.save(((SecurityUser) user).user());
        } catch (DataAccessException ex) {
            handleException(user,
                    ex,
                    "Unable to create user with name %s");
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        //todo implement the method
    }

    @Override
    public void deleteUser(String username) {
        //todo implement the method
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        //todo implement the method

    }

    @Override
    public boolean userExists(String username) {
        //todo implement the method
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserName(username);
        User u = userOptional.orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("Error::: username %s not found.", username)
                ));
        return new SecurityUser(u);
    }

    private void handleException(UserDetails user,
                                 RuntimeException dataAccessException,
                                 String msgTemplate) {
        String exceptionMessage = DEFAULT_ERR_MSG;
        try {
            exceptionMessage = String.format(msgTemplate, user.getUsername());
            log.error(exceptionMessage);
        } catch (IllegalFormatException illegalFormatException) {
            log.error("Unable to format msg template with the supported arguments. Default will be used.");
        }
        throw new UserCreationException(exceptionMessage, dataAccessException);
    }
}
