package com.snehasish.ssc3.configs;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        val userDetailsService = new InMemoryUserDetailsManager();
        val user1 = User.withUsername("testuser").password(passwordEncoder.encode("12345")).authorities("read", "write").build();
        val user2 = User.withUsername("testuser2").password(passwordEncoder.encode("67890")).authorities("read", "write").build();
        userDetailsService.createUser(user1);
        userDetailsService.createUser(user2);
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
