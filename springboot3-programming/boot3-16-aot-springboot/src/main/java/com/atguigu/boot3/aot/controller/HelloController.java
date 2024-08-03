package com.atguigu.boot3.aot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger
 * @Description
 * @create 2024-08-03
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot AOT!!!";
    }
}
