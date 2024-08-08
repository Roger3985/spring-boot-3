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
 * 2. R2dbcDataAutoConfiguration: 主要給用戶提供了 R2dbcEntityTemplate 可以進行 CRUD 操作
 *    -> R2dbcEntitTemplate: 操作資料庫的響應式客戶端；提供 CRUD API；RedisTemplate XxxTemplate
 *    -> 資料類型映射關係、轉換器、自定義 R2dbcCustomConversions 轉換器組件
 *       資料類型轉換: int、Integer； varchar、String；datetime、Instant
 * 3. R2dbcRepositoriesAutoConfiguration: 開啟 Spring Data 聲明式介面方式的 CRUD；
 *    -> mybatis-plus: 提供了 BaseMapper、IService；自帶的 CRUD 功能。
 *    -> Spring Data: 提供了基礎了 CRUD 介面，不用寫任何實現的情況下，可以直接具有 CRUD 功能。
 * 4. R2dbcTransactionManagerAutoConfiguration: 交易管理
 */
@SpringBootApplication
public class Chaptero4R2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chaptero4R2dbcApplication.class, args);
	}

}
