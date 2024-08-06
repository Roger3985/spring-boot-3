package com.atguigu.webflux;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;
import java.net.URI;

/**
 * @author Roger
 * @Description
 * @create 2024-08-06
 */
public class FluxMainApplication {
    public static void zzz(String[] args) throws IOException {
        // 快速自己編寫一個能夠處理請求的服務器

        // 1. 創建一個能夠處理 Http 請求的處理器。參數：請求、響應; 返回值：Mono<Void>: 代表處理完成的信號
        HttpHandler handler = FluxMainApplication::handle;

        // 2. 啟動一個服務器，監聽 8080 端口，接受資料，拿到資料交給 HttpHandler 進行請求處理
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);

        // 3. 啟動 Netty 服務器
        HttpServer
                .create()
                .host("localhost")
                .port(8080)
                .handle(adapter) // 用指定的處理器處理請求
                .bindNow(); // 現在就綁定

        System.out.println("服務器啟動完成...監聽8080，接受請求");
        System.in.read();
        System.out.println("服務器停止...");
    }

    private static Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response) {
        URI uri = request.getURI();
        System.out.println("請求進來了: " + uri);

        // 編寫請求處理的業務，給瀏覽器寫一個內容 URL + "Hello~!"
//        response.getHeaders(); // 獲取響應頭
//        response.getCookies(); // 獲取 Cookie
//        response.getStatusCode(); // 獲取響應狀態碼
//        response.bufferFactory(); // buffer 工廠
//        response.writeWith(); // 把 xxx 寫出去
//        Mono<Void> voidMono = response.setComplete(); // 響應結束

        // 資料的發佈者：Mono<DataBuffer>、Flux<DataBuffer>
        // 創建 響應資料的 DataBuffer
        DataBufferFactory factory = response.bufferFactory();

        // 資料 buffer
        DataBuffer buffer = factory.wrap(new String(uri.toString() + " ==>  Hello!~").getBytes());

        // 需要一個 DataBuffer 的發佈者
        Mono<Void> voidMono = response.writeWith(Mono.just(buffer));

        // 編寫請求處理的業務
        return voidMono;
    }
}
