package com.atguigu.boot3.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger
 * @Description
 * @create 2024-08-01
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello!Spring Security";
    }

    @PreAuthorize("hasRole('world_exec')")
    @GetMapping("/world")
    public String world() {
        return "Hello!Spring World";
    }

}
