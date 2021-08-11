package com.snehasish.ssc2.config;

import com.snehasish.ssc2.dao.UserRepository;
import com.snehasish.ssc2.service.DBUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new DBUserDetailsService(userRepository);
    }


}
