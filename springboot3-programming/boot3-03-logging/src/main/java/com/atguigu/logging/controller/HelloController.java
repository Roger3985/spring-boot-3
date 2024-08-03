package com.atguigu.logging.controller;

import lombok.extern.log4j.Log4j2;
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
    public String hello(String a, String b) {
        log.trace("trace 日誌......");
        log.debug("debug 日誌......");
        // SpringBoot  底層默認的日誌級別 info
        log.info("info 日誌...... 參數a:{} b:{}", a, b);
        log.warn("warn 日誌......");
        log.error("error 日誌......");
        // log.info("哈哈哈，方法進來了");
        // logger.info("哈哈哈，方法進來了");
        return "hello";
    }
}
