package com.atguigu.r2dbc;

import com.atguigu.webflux.entity.TAuthor;
import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.*;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-07
 */
public class R2DBCTest {

    /*
        思想:
        1. 有了 R2DBC，我們的應用在資料庫層面是天然支持高併發、高吞吐量。
        2. 並不能提高開發效率。
     */

    @Test
    void connection() {
        /*
            r2dbc: 基於全異步、響應式、消息驅動
         */
        // jdbc:postgres://localhost:5432/test
        // 1. 獲取連接工廠
        ConnectionFactory connectionFactory =
                ConnectionFactories.get("r2dbc:mysql://localhost:3306/test");

        // 2. 獲取連接，發送 SQL
        // JDBC: Statement: 封裝 sql 的

        // 3. 資料的發佈者
        Mono.from(connectionFactory.create())
                .flatMapMany(connection ->
                                    connection.createStatement("select * from t_author where id=?id and name=?name")
                                    .bind("id", 1L) // 具名參數的方式
                                            .bind("name", "張三")
                                    .execute()
                ).flatMap(result -> {
                    return result.map(readable -> {
                        Long id = readable.get("id", Long.class);
                        String name = readable.get("name", String.class);
                        return new TAuthor(id, name);
                    });
                }).subscribe(tAuthor -> System.out.println("tAuthor" + tAuthor));
    }

    @Test
    void connection2() throws IOException {
        // Postgres 的相關配置
        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration
                .builder()
                .host("localhost")
                .port(3306)
                .username("root")
                .password("123456")
                .database("test")
                .build();

        // 1. 獲取連接工廠
        MySqlConnectionFactory connectionFactory = MySqlConnectionFactory.from(configuration);

        // 3. 資料的發佈者
        Mono.from(connectionFactory.create())
                .flatMapMany(connection ->
                        connection.createStatement("select * from t_author where id=?")
                                .bind(0, 1L)
                                .execute()
                ).flatMap(result -> {
                    return result.map(readable -> {
                        Long id = readable.get("id", Long.class);
                        String name = readable.get("name", String.class);
                        return new TAuthor(id, name);
                    });
                })
                .take(5) // 只取其中五條
                .subscribe(tAuthor -> System.out.println("tAuthor" + tAuthor));


        // 背壓: 不用返回所有東西，基於請求量返回。

        System.in.read();
    }
}
