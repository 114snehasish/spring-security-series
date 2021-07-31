package com.snehasish.ssc1.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping
    public String test(Authentication authentication) {
        return "Hello " + authentication.getName();
    }
}
