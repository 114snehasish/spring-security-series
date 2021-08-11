package com.snehasish.ssc2.service;

import com.snehasish.ssc2.dao.UserRepository;
import com.snehasish.ssc2.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class DBUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        User u = userOptional.orElseThrow(() -> new UsernameNotFoundException("Error::: username " + userName + " not found."));
        return new SecurityUser(u);
    }
}
