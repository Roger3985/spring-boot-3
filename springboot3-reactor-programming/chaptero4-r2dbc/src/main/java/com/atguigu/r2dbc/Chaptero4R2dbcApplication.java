package com.atguigu.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Roger
 * @Description
 * @create 2024-08-07
 *
 * SpringBoot 對 r2dbc 的自動配置
 * 1. R2dbcAutoConfiguration: 主要配置連接工廠，連接池
 * 2. R2dbcDataAutoConfiguration
 *    -> R2dbcEnt
 * 3. R2dbcRepositoriesAutoConfiguration
 * 4. R2dbcTransactionManagerAutoConfiguration
 */
@SpringBootApplication
public class Chaptero4R2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chaptero4R2dbcApplication.class, args);
	}

}
