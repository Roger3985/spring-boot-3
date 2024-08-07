package com.atguigu.r2dbc;

import com.atguigu.webflux.entity.TAuthor;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Statement;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                ConnectionFactories.get("r2dbc:postgres://localhost:5433/test");

        // 2. 獲取連接，發送 SQL
        // JDBC: Statement: 封裝 sql 的

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
                }).subscribe(tAuthor -> System.out.println("tAuthor" + tAuthor));
    }
}
