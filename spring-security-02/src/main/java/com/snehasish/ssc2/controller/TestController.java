package com.snehasish.ssc2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping
    public String hello(){
        return "The quick brown fox jumps over the lazy dog";
    }
}
