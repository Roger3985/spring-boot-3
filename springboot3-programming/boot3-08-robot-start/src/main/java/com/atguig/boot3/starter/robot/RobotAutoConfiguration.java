package com.atguig.boot3.starter.robot;

import com.atguig.boot3.starter.robot.controller.RobotController;
import com.atguig.boot3.starter.robot.properties.RobotProperties;
import com.atguig.boot3.starter.robot.service.RobotService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
// 給容器中導入 Robot 功能要用的所有組件。
@Import({RobotController.class, RobotProperties.class, RobotService.class})
@Configuration
public class RobotAutoConfiguration {

//    @Import({RobotController.class}) 等於以下
//    @Bean  // 把組件導入容器中即可
//    public RobotController robotController() {
//        return new RobotController();
//    }

}
