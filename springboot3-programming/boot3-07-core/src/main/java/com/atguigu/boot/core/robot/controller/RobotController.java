package com.atguigu.boot.core.robot.controller;

import com.atguigu.boot.core.robot.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@RestController
public class RobotController {

    @Autowired
    RobotService robotService;

    @GetMapping("/robot/hello")
    public String sayHello() {
        String s = robotService.sayHello();
        return s;
    }
}