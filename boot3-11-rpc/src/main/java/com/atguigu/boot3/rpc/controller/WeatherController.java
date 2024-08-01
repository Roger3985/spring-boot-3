package com.atguigu.boot3.rpc.controller;

import com.atguigu.boot3.rpc.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather")
    public String weather(@RequestParam("city") String city) {
        // 查詢天氣
        Mono<String> weather = weatherService.weather(city);
        return "目前執行成功，但因為尚未獲取授權碼，會 500 是正常的";
    }
}
