package com.atguigu.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.beans.Transient;
import java.time.Duration;

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

    // test / event-stream
    // SSE 測試: chatgpt 都在用
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sse() {
        return Flux.range(1, 10)
                .map(i -> "ha" + i)
                .delayElements(Duration.ofMillis(500))
                .cache(10);
    }

    // SpringMVC: 以前怎麼用，基本可以無縫切換
    // 底層：需要自己開始編寫響應式代碼

    public Flux<ServerSentEvent<String>> sse2() {
        return Flux.range(1, 10)
                .map(i -> {
                    // 構建一個 SSE 物件
                    return ServerSentEvent.builder("ha-" + i)
                            .id(i + "")
                            .comment("hei-" + i)
                            .event("haha")
                            .build();
                })
                .delayElements(Duration.ofMillis(500));
    }

}
