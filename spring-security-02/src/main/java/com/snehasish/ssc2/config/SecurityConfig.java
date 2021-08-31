package com.snehasish.ssc2.config;

import com.snehasish.ssc2.dao.UserRepository;
import com.snehasish.ssc2.model.User;
import com.snehasish.ssc2.service.DBUserDetailsService;
import com.snehasish.ssc2.service.SecurityUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository, PasswordEncoder encoder) {
        var userDetailsManager = new DBUserDetailsService(userRepository);
        userDetailsManager.createUser(new SecurityUser(User.builder().userName("jdoe").password(encoder.encode("12345")).build()));
        return userDetailsManager;
    }


}
