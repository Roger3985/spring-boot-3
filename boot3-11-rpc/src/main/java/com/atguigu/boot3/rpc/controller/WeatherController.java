package com.atguigu.boot3.rpc.controller;

import com.atguigu.boot3.rpc.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        weatherService.weather(city);
        return
    }
}
