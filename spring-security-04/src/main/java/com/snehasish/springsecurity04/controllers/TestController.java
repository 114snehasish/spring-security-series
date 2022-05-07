package com.snehasish.springsecurity04.controllers;

import com.snehasish.springsecurity04.mappers.UserToDtoMapper;
import com.snehasish.springsecurity04.models.SecurityUser;
import com.snehasish.springsecurity04.models.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public record TestController(
        UserToDtoMapper userToDtoMapper
) {
    @GetMapping("/getUserDetails")
    public UserDto getUserDetails(Authentication auth) {
        val user = ((SecurityUser) auth.getPrincipal()).user();
        return userToDtoMapper.entityToDto(user);
    }
}
