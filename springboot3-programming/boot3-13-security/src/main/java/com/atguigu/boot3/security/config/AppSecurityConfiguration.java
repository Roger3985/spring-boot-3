package com.atguigu.boot3.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */

/**
 * 1. 自定義請求授權規則: http.authorizeHttpRequests
 * 2. 自定義登入規則: http:formLogin
 * 3. 自定義用戶信息查詢規則: UserDetailsService
 * 4. 開啟方法級別的精確權限控制: @EnableMethodSecurity + @PreAuthorize("hasRole('自定義')")
 */
@EnableMethodSecurity
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
        // http.formLogin(); // 目前此項已經被淘汰，但還能夠使用
        http.formLogin(formLogin -> {
            formLogin.loginPage("/login").permitAll(); // 自定義登入頁位置，並且所有人都能訪問
        });

        return http.build();
    }

    @Bean // 查詢用戶詳情;
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails roger1 = User.withUsername("roger1")
                .password(passwordEncoder.encode("123456")) // 使用密碼加密器加密密碼進行儲存
                .roles("ADMIN", "hr")
                .authorities("file_read", "file_write")
                .build();

        UserDetails roger2 = User.withUsername("roger2")
                .password(passwordEncoder.encode("123456"))
                .roles("hr")
                .authorities("file_read")
                .build();

        /*
            要解決 roger3 用戶登入後出現 403 Forbidden 錯誤，您需要確認以下幾點：確保角色和權限配置正確：

            @PreAuthorize("hasRole('world_exec')") 是用來檢查角色的，並不是權限。hasRole 檢查的是 ROLE_world_exec。
            您需要確認您用戶的角色配置是否正確。
            正確的角色和權限配置：

            通常，角色需要以 ROLE_ 作為前綴。例如，角色應該設置為 ROLE_world_exec。
            如果您想要檢查權限而不是角色，可以使用 @PreAuthorize("hasAuthority('world_exec')")。
         */
        UserDetails roger3 = User.withUsername("roger3")
                .password(passwordEncoder.encode("123456"))
                .roles("ADMIN")
                .authorities("file_write", "ROLE_world_exec") // 要給予授權前面需要加上 ROLE // 確保權限正確
                .build();

        // 默認內存中保存所有的用戶信息
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(roger1, roger2, roger3);
        return manager;
    }

    @Bean // 密碼加密器
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
