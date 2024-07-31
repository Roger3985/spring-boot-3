package com.atguigu.boot3.edis.controller;

import com.atguigu.boot3.edis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
@RestController
public class RedisTestController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    // 為了後來系統的兼容性，應該所有的物件都是以 json 的方式進行保存

    @Autowired // 如果給 redis 中保存資料會使用默認的序列化機制，導致 redis 中保存的物件不可視
    RedisTemplate<Object, Object> redisTemplate;

    @GetMapping("/count")
    public String count() {

        Long hello = stringRedisTemplate.opsForValue().increment("hello");

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

    @GetMapping("/person/save")
    public String savePerson() {
        Person person = new Person(1, "Roger", 18, new Date());

        // 1. 序列化：物件轉為字符串方式
        redisTemplate.opsForValue().set("person", person);

        return "ok";
    }

    @GetMapping("/person/get")
    public Person getPerson() {
        Person person = (Person) redisTemplate.opsForValue().get("person");
        return person;
    }
}
