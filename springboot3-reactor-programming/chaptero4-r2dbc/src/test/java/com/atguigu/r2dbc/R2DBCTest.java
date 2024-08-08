package com.atguigu.r2dbc;

import com.atguigu.r2dbc.entity.TAuthor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-08
 */
@SpringBootTest
public class R2DBCTest {
    // Spring Data 提供的兩個核心底層組件
    // R2dbcEntityTemplate: join 查詢不好做
    @Autowired
    R2dbcEntityTemplate r2dbcEntityTemplate; // CRUD API // 更多操作範例請參照文檔

    // DatabaseClient 貼近底層，join 操作好做，複雜查詢好用
    @Autowired
    DatabaseClient databaseClient; // 資料庫客戶端

    @Test
    void r2dbcEntityTemplateTest() throws IOException {
        // Query By Criteria: QBC

        // 1. Criteria 構造查詢條件 This mean is where id=1 and name="張三"
        Criteria criteria = Criteria.empty();
        criteria.and("id")
                        .is(1L)
                                .and("name")
                                        .is("張三");

        // 2. 封裝為 Query 物件
        Query query = Query
                .query(criteria);

        r2dbcEntityTemplate
                .select(query, TAuthor.class)
                .subscribe(tAuthor -> System.out.println("tAuthor: " + tAuthor));

        System.in.read();
    }

    @Test // 底層操作
    void databaseClient() throws IOException {
        databaseClient.sql("select * from t_author where id=?id")
                .bind(0, 2L)
                .fetch() // 抓取資料
                .all() // 返回所有
                .map(map -> { // map == bean 屬性 = 值
                    System.out.println("map = " + map);
                    String id = map.get("id").toString();
                    String name = map.get("name").toString();
                    return new TAuthor(Long.parseLong(id), name);
                })
                .subscribe(tAuthor -> System.out.println("tAuthor = " + tAuthor));

        System.in.read();
    }

}
