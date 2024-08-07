package com.atguigu.webflux.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-07
 */
@Component
public class WebFilter implements org.springframework.web.server.WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        System.out.println("請求處理放行到目標方法之前");
        // 補充: 流一旦經過某個操作之後就會變成新流
        Mono<Void> filter = chain
                .filter(exchange) // 放行
                .doOnError(error -> {
                    System.out.println("目標方法發生異常以後...");
                }) // 目標方法發生異常後座是
                        .doFinally(signalType -> {
                            System.out.println("目標方法執行以後...");
                        }); // 目標方法執行之後
        // 上面執行不花時間
        System.out.println("目標方法執行以後...");
        return filter; // 看清楚返回的是誰 !!
    }
}
