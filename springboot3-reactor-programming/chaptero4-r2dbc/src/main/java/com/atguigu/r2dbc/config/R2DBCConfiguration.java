package com.atguigu.r2dbc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-08
 */
@EnableR2dbcRepositories // 開啟 R2DBC 倉庫功能: jpa
@Configuration
public class R2DBCConfiguration {
}
