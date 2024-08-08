package com.atguigu.r2dbc;

import com.atguigu.r2dbc.entity.TAuthor;
import com.atguigu.r2dbc.entity.TBook;
import com.atguigu.r2dbc.repositories.AuthorRepositories;
import com.atguigu.r2dbc.repositories.BookRepositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

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

    @Autowired
    AuthorRepositories authorRepositories;

    @Autowired
    BookRepositories bookRepositories;

    // 簡單查詢: 人家直接提供好介面
    // 複雜條件查詢: 1. QBE API 2.自定義方法 3. 自定義 SQL

    @Test
    void oneToMany() throws IOException {
//        databaseClient.sql("select a.id aid, a.name, b.* from t_author a " +
//                "left join t_book b on a.id = b.author_id " +
//                "order by  a.id")
//                .fetch()
//                .all()

        // 1~6
        // 1: false 2: false 3:false 4:true 5:false 6:false
        // [1,2,3]
        // [4]
        // [5,6]
        // bufferUntilChanged: 如果下一個判定值比起上一個發生了變化就開一個新的 buffer 保存，如果沒有變化就保存到原有的 buffer 中
        Flux.just(1, 2, 3, 4, 5, 6)
                .bufferUntilChanged(integer -> integer % 4 == 0) // 自帶分組
                .subscribe(list -> System.out.println("list = " + list));

        databaseClient.sql("select a.id aid, a.name,  b.* from t_author a " +
                "left join t_book b on a.id = b.author_id " +
                "order by a.id")
                        .fetch()
                                .all()
                                        .bufferUntilChanged(rowMap -> Long.parseLong(rowMap.get("aid").toString())) // Long 數字緩存 -127 ~ 127
                .map(list -> {
                    TAuthor tAuthor = new TAuthor();
                    Map<String, Object> map = list.get(0);
                    tAuthor.setId(Long.parseLong(map.get("aid").toString()));
                    tAuthor.setName(map.get("name").toString());
                    // 查到的所有圖書
                    list.stream()
                                    .map(element -> {
                                        return new TBook();
                                    })
                    tAuthor.setBooks();
                })
        // 物件比較需要自己寫好 equals 方法

        System.in.read();
    }

    @Test
    void author() throws IOException {
        authorRepositories.findById(1L)
                .subscribe(tAuthor -> System.out.println("tAuthor = " + tAuthor));

        System.in.read();
    }

    @Test
    void book() throws IOException {
//        bookRepositories.findAll()
//                .subscribe(tBook -> System.out.println("tBook = " + tBook));

//        bookRepositories.hahaAuthor(1L)
//                        .subscribe(tBook -> System.out.println("tBook = " + tBook));
        // 1:1 的第一種方式
//        bookRepositories.hahaAuthor(1L)
//                .subscribe(tBook -> System.out.println("tBook = " + tBook));

        //  1：1 的第二種方式
        databaseClient.sql("select b.*, t.name as name from t_book b" +
                " LEFT JOIN t_author t on b.author_id = t.id " +
                " WHERE b.id = ?")
                .bind(0, 1L)
                .fetch()
                .all()
                .map(row -> {
                    String id = row.get("id").toString();
                    String title = row.get("title").toString();
                    String authorId = row.get("author_id").toString();
                    String name = row.get("name").toString();
                    TBook tBook = new TBook();
                    tBook.setId(Long.parseLong(id));
                    tBook.setTitle(title);

                    TAuthor tAuthor = new TAuthor();
                    tAuthor.setName(name);
                    tAuthor.setId(Long.parseLong(id));

                    tBook.setAuthor(tAuthor);

                    return tBook;
                })
                .subscribe(tBook -> System.out.println("tBook = " + tBook));
        // 兩種辦法：
        // 1. 一次查詢出來，封裝好
        // 2. 兩次查詢

        // 1:N：一個作者，可以查詢很多的圖書

        System.in.read();
    }

    @Test
    void authorRepositories() throws IOException {

        // statement
        // [SELECT t_author.id, t_author.name, FROM t_author WHERE t_author.id IN (?, ?)
        // AND (t_author.name LIKE ?)] // 方法起名
        authorRepositories.findAllByIdInAndNameLike(
                Arrays.asList(1L, 2L),
                "張%"
        ).subscribe(tAuthor -> System.out.println("tAuthor = " + tAuthor));

        // 自定義 @Query 註解
        authorRepositories.findHaHa()
                .subscribe(tAuthor -> System.out.println("tAuthor = " + tAuthor));
         
        authorRepositories.findAll()
                .subscribe(tAuthor -> System.out.println("tAuthor = " + tAuthor));

        System.in.read();
    }

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
                    return new TAuthor(Long.parseLong(id), name, null);
                })
                .subscribe(tAuthor -> System.out.println("tAuthor = " + tAuthor));

        System.in.read();
    }

}
