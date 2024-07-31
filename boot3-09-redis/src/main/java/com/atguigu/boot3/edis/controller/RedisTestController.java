package com.atguigu.boot3.edis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
@RestController
public class RedisTestController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/count")
    public String count() {

        Long hello = redisTemplate.opsForValue().increment("hello");

        /*
            常見的資料類型(key:value) value 可以有很多類型，以下可見
            1. string：普通字符串:         redisTemplate.opsForValue()
            2. list:   列表:              redisTemplate.opsForList()
            3. set:    集合:              redisTemplate.opsForSet()
            4. zset:   有序集合:           redisTemplate.opsForZSet()
            5. hash:   key:value(map結構):redisTemplate.opsForHash()
         */

        return "訪問了[" + hello + "]";
    }
}
