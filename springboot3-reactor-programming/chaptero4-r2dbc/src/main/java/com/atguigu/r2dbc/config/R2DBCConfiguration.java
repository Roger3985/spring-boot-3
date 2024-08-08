package com.atguigu.r2dbc.config;

import com.atguigu.r2dbc.config.converter.BookConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-08
 */
@EnableR2dbcRepositories // 開啟 R2DBC 倉庫功能: jpa
@Configuration
public class R2DBCConfiguration {

    @Bean
    @ConditionalOnMissingBean // 替換容器中原來的
    public R2dbcCustomConversions conversions() {
        // 把我們的轉換器加入進去
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, new BookConverter());
    }
}
