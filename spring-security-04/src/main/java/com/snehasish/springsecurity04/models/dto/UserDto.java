package com.snehasish.springsecurity04.models.dto;

import java.io.Serializable;

public record UserDto(
        Long id,
        String username,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled) implements Serializable {
}
