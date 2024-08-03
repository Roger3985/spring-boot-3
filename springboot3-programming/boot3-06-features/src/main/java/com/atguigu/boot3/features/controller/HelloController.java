package com.atguigu.boot3.features.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-30
 */
@RestController
public class HelloController {

    @Value("${haha}")
    String haha;

    @GetMapping("/haha")
    public String haha() {
        return haha;
    }
}
