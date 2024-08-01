package com.atguigu.boot3.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
@Configuration
public class AppSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 請求授權
        http.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/").permitAll() // 1. 首頁所有人都允許
                    .anyRequest().authenticated(); // 2. 剩下的任意請求都需要進行認證 (登入)
        });

        // 表單登入功能
        // 3. 表單登入功能: 開啟默認的表單登入功能: Spring Security 提供默認的登入頁(沒有這一項寫入，僅僅寫了上面授權功能，會將預設的表單登入禁用)
        http.formLogin(); // 目前此項已經被淘汰，但還能夠使用

        return http.build();
    }
}
