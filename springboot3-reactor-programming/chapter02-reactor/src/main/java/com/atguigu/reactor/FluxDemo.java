package com.atguigu.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

/**
 * @author Roger
 * @Description
 * @create 2024-08-04
 */
public class FluxDemo {

    public static void main(String[] args) {

    }

    // 測試 Flux
    public void flux() throws IOException {
        // Mono: 0|1 個元素的流
        // Flux: N 個元素的流
        // 發佈者發佈資料：：源頭

        // 1. 多元素的流
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5);

        // 2. 流不消費就沒用; 消費：訂閱
        just.subscribe(element -> System.out.println("element1 = " + element));
        // 一個資料流可以有很多個消費者
        just.subscribe(element -> System.out.println("element2 = " + element));

        // 對於每個消費者來說都是一樣的：廣播模式
        System.out.println("======================");
        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));// 每秒產生一個從 0 開始的遞增數字
        flux.subscribe(System.out::println);

        System.in.read();
    }
}
