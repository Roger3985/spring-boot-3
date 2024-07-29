package com.atguigu.boot3.features.config;

import com.atguigu.boot3.features.bean.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Roger
 * @Description
 * @create 2024-07-30
 */
@Profile("test") // 只有指定環境被激活，整個類的所有配置才能生效。
@Configuration
public class MyConfig {

    @Profile("dev")
    @Bean
    public Cat cat() {
        return new Cat();
    }
}
