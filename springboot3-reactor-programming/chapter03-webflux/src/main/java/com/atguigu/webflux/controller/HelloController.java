package com.atguigu.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Roger
 * @Description
 * @create 2024-08-06
 */
@RestController
public class HelloController {

    // Webflux：向下兼容原來 SpringMVC 的大多數註解和 API
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "key", required = false, defaultValue = "哈哈") String key) {
        return "Hello World!!! key = " + key;
    }

    /*
        現在響應式編程推薦的方式
        1. 返回單個資料 Mono: Mono<Order>、Mono<User>、Mono<String>、Mono<Map>
        2. 返回多個資料 Flux: Flux<Order>、Flux<User>、Flux<String>、Flux<Map>
        3. 配合 Flux，完成 SSE： Server Send Event; 服務端事件推送
     */
    @GetMapping("/haha")
    public Mono<String> haha() {
        return Mono.just("哈哈哈");
    }

    @GetMapping("/hehe")
    public Flux<String> hehe() {
        return Flux.just("hehe1", "hehe2");
    }

}
