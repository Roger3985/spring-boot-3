package com.atguigu.boot3.rpc.service;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
public interface WeatherInterface {

    @GetExchange(url = "https://api.example.weather.com", accept = "application/json") // "這個是發請求出去的"
    Mono<String> getWeather(@RequestParam("area") String city); // 授權頭已經全權調用到 config 統一管理
}
