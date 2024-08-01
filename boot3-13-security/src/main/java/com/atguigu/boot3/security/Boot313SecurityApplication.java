package com.atguigu.boot3.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Security 場景的自動配置類:
 * SecurityAutoConfiguration、SpringBootWebSecurityConfiguration、SecurityFilterAutoConfiguration
 * 1. security 的所有配置在 SecurityProperties: 以 spring.security 開頭
 * 2. 默認的 SecurityFilterChain 組件:
 * 	  - 所有請求都需要進行認證 (登入)
 * 	  - 開啟了表單登入: spring security 提供了一個默認的登入頁，未經登入的所有請求都需要進行登入。
 * 	  - 也開啟了基於 httpBasic 的驗證登入方式
 * 	  由此可知
 * 	  @Configuration(
 *         proxyBeanMethods = false
 *     )
 *     @ConditionalOnDefaultWebSecurity
 *     static class SecurityFilterChainConfiguration {
 *         SecurityFilterChainConfiguration() {
 *         }
 *
 *         @Bean
 *         @Order(2147483642)
 *         SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
 *             http.authorizeHttpRequests((requests) -> {
 *                 ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated();
 *             });
 *             http.formLogin(Customizer.withDefaults());
 *             http.httpBasic(Customizer.withDefaults());
 *             return (SecurityFilterChain)http.build();
 *         }
 *     }
 * 4. @EnableWebSecurity 生效
 *    - WebSecurityConfiguration 生效: web 安全配置
 *    - HttpSecurityConfiguration 生效: http 安全規則
 *    - @EnableGlobalAuthentication 生效: 全局認證生效
 *    	-> AuthenticationConfiguration: 認證配置
 * 5.
 */
@SpringBootApplication
public class Boot313SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(Boot313SecurityApplication.class, args);
	}

}
