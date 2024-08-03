package com.atguigu.boot3.actuator.controller;

import com.atguigu.boot3.actuator.component.MyHahaComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger
 * @Description
 * @create 2024-08-02
 */
@RestController
public class HelloController {

    @Autowired
    MyHahaComponent myHahaComponent;

    @GetMapping("/hello")
    public String hello() {
        // 業務調用
        myHahaComponent.hello();
        return "哈哈哈";
    }

}
