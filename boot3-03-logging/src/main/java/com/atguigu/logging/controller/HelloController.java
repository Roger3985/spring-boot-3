package com.atguigu.logging.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger
 * @Description
 * @create 2024-07-28
 */
@Slf4j
@RestController
public class HelloController {

    // Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/hello")
    public String hello() {
        log.info("哈哈哈，方法進來了");
        // logger.info("哈哈哈，方法進來了");
        return "hello";
    }
}
