package com.snehasish.springsecurity04.configs;

import com.snehasish.springsecurity04.dao.UserRepository;
import com.snehasish.springsecurity04.models.SecurityUser;
import com.snehasish.springsecurity04.models.User;
import com.snehasish.springsecurity04.services.DBUserDetailsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@Slf4j
public class AuthenticationConfig {
    @Bean
    public DBUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder,
                                                   UserRepository userRepository) {
        var userDetailsManager = new DBUserDetailsManager(userRepository);
        var user1 = User.builder()
                .username("jdoe")
                .password(passwordEncoder.encode("12345"))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();
        var user2 = User.builder()
                .username("mstone")
                .password(passwordEncoder.encode("67890"))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();
        userDetailsManager.createUser(new SecurityUser(user1));
        userDetailsManager.createUser(new SecurityUser(user2));
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
