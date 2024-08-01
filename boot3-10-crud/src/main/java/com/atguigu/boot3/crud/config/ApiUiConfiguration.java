package com.atguigu.boot3.crud.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
@Configuration
public class ApiUiConfiguration {

    /**
     * 分組設置
     * @return
     */
    @Bean
    public GroupedOpenApi empApi() {
        return GroupedOpenApi.builder()
                .group("員工管理")
                .pathsToMatch("/emp/**", "/emps/**")
                .build();
    }

    /**
     * 分組設置
     * @return
     */
    @Bean
    public GroupedOpenApi deptApi() {
        return GroupedOpenApi.builder()
                .group("部門管理")
                .pathsToMatch("/dept/**", "/depts/**")
                .build();
    }

    @Bean
    public OpenAPI docsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpringBoot-CRUD API")
                        .description("專門測試API的文件")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot-CRUD API Documentation")
                        .url("https://github.com/Roger3985/spring-boot-3/tree/master/boot3-10-crud"));

    }
}
