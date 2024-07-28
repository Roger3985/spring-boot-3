package com.atguigu.boot304web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger
 * @Description
 * @create 2024-07-28
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello2() {
        return "hello";
    }

    /**
     * 默認使用新版的 PathPatternParser （路徑匹配器）進行匹配。
     * 不能匹配 ** 在中間的情況，剩下的跟 antPathMatcher(spring 5.3 以前，現已經不是默認使用） 語法兼容。
     * 需要使用 ** 在中間，需要再 properties 中改變路徑匹配策略成：ant_path_matcher 老版策略，注意：path_pattern_parser 是新版策略。
     * @param request
     * @param path
     * @return
     */
    @GetMapping("/a*/b?/{p1:[a-f]+}/**")
    public String hello(HttpServletRequest request, @PathVariable("p1") String path) {
        log.info("路徑變量 p1: {}", path);
        String uri = request.getRequestURI();
        log.info(uri);
        return uri;
    }
}
