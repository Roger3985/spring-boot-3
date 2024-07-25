package com.atguigu.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger
 * @Description
 * @create 2024-07-25
 */
// @ResponseBody
// @Controller
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hell() {
        return "Hello, Spring Boot 3";
    }
}
