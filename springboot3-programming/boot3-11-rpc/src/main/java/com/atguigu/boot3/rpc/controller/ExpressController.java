package com.atguigu.boot3.rpc.controller;

import com.atguigu.boot3.rpc.service.ExpressApi;
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
public class ExpressController {

    @Autowired
    ExpressApi expressApi;

    @GetMapping("/express")
    public String express(@RequestParam("number") String number) {
        // 獲取物流，已經將授權碼統一交由 config 統一進行管理。
        Mono<String> express = expressApi.getExpress(number);
        return "目前執行成功，但因為尚未獲取授權碼，會 500 是正常的";
    }
}
