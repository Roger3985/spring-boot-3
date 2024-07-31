package com.atguig.boot3.starter.robot.service;

import com.atguig.boot3.starter.robot.properties.RobotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@Service
public class RobotService {

    @Autowired
    RobotProperties robotProperties;

    public String sayHello() {
        return "你好: 名字: 【" + robotProperties.getName() + "】; 年齡: 【" + robotProperties.getAge() + "】";
    }
}
