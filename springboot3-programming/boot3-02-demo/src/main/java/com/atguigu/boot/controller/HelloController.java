package com.atguigu.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger
 * @Description
 * @create 2024-07-25
 */
@RestController
public class HelloController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/incr")
    public String incr() {
        Long haha = redisTemplate.opsForValue().increment("haha");
        return "增加的數值：" + haha;
    }
}
