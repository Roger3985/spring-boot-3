package com.atguigu.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Roger
 * @Description  啟動 Spring
 * @create 2024-07-25
 */
// 主程式所在的包： com.atguigu.boot
// com.atguigu.boot.controller (因為是同層級所以可以掃描得到)
// 第一種自定義掃包方式：scanBasePackages 自定義掃描路徑
// 第二種自定義掃包方式：
// @SpringBootConfiguration
// @EnableAutoConfiguration
// @ComponentScan()
@SpringBootApplication(scanBasePackages = "com.atguigu") // 這是一個 SpringBoot 應用
public class MainApplication {
        public static void main(String[] args) {
            // java 10 : 局部變量類型的自動推斷機制
            var ioc = SpringApplication.run(MainApplication.class, args);
            // 1. 獲取容器中所有組件的名字
            String[] names = ioc.getBeanDefinitionNames();
            // 2. 遍歷：dispatcherServlet、beanNameViewResolver、characterEncodingFilter、multipartResolver
            // SpringBoot 把以前配置的核心組件現在都給我們自動配置好了。
            for (String name : names) {
                System.out.println(name);
            }

        }
}
