package com.snehasish.ssc2.service;

import com.snehasish.ssc2.dao.UserRepository;
import com.snehasish.ssc2.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
public class DBUserDetailsService implements UserDetailsManager {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        User u = userOptional.orElseThrow(() -> new UsernameNotFoundException("Error::: username " + userName + " not found."));
        return new SecurityUser(u);
    }

    @Override
    @Transactional
    public void createUser(UserDetails userDetails) {
        userRepository.save(((SecurityUser) userDetails).getUser());
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return false;
    }
}
