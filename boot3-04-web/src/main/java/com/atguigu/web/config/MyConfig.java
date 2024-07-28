package com.atguigu.web.config;

import com.atguigu.web.component.MyYamlHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Roger
 * @Description
 * @create 2024-07-28
 */
// @EnableWebMvc // 禁用 boot 的默認設置
@Configuration // 這是一個配置類，給容器中放一個 WebMvcConfigurer 組件，就能自定義底層。
public class MyConfig /* implements WebMvcConfigurer */ {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // 自己寫新的規則
                registry.addResourceHandler("/static/**")
                        .addResourceLocations("classpath:/a/", "classpath:/b/")
                        .setCacheControl(CacheControl.maxAge(7200, TimeUnit.SECONDS));
            }

            @Override // 配置攔截器
            public void addInterceptors(InterceptorRegistry registry) {
                WebMvcConfigurer.super.addInterceptors(registry);
            }

            @Override // 配置一個能把物件轉換為 yaml 的 messageConverter
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MyYamlHttpMessageConverter());
            }
        };
    }

//    @Override // 配置靜態資源
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 保留以前的規則（要注意不能加上 @EnableWebMvc 因為會禁用 boot 的默認設置）
//        // WebMvcConfigurer.super.addResourceHandlers(registry);
//
//        // 自己寫新的規則
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/a/", "classpath:/b/")
//                .setCacheControl(CacheControl.maxAge(7200, TimeUnit.SECONDS));
//    }
//
//    @Override // 配置攔截器
//    public void addInterceptors(InterceptorRegistry registry) {
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
}
