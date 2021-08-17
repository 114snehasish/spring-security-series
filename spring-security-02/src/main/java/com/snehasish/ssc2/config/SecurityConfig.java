package com.snehasish.ssc2.config;

import com.snehasish.ssc2.dao.UserRepository;
import com.snehasish.ssc2.model.User;
import com.snehasish.ssc2.service.DBUserDetailsService;
import com.snehasish.ssc2.service.SecurityUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        var userDetailsManager = new DBUserDetailsService(userRepository);
        userDetailsManager.createUser(new SecurityUser(User.builder().userName("jdoe").password("12345").build()));
        return userDetailsManager;
    }


}
