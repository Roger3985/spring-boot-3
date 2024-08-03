package com.atguig.boot3.starter.robot.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@ConfigurationProperties(prefix = "robot") // 此屬性類和配置文件指定前綴綁定
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RobotProperties {
    private String name;
    private String age;
    private String email;
}
