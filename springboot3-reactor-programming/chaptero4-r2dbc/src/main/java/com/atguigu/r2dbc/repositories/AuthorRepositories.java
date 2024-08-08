package com.atguigu.r2dbc.repositories;

import com.atguigu.r2dbc.entity.TAuthor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-08
 */
@Repository
public interface AuthorRepositories extends R2dbcRepository<TAuthor, Long> {
    // 默認繼承一堆 CRUD 的方法，像 mybatis-plus

    // QBC: Query By Criteria
    // QBE: Query By Example

    // 成為一個起名工程師 where id In () and name like ?
    // 僅限單表複雜查詢
    Flux<TAuthor> findAllByIdInAndNameLike(Collection<Long> id, String name);

    // 多表複雜查詢
    @Query("select * from t_author") // 自定義 query 註解，指定 sql 語句
    Flux<TAuthor> findHaHa();

    // 1-1: 關聯
    // 1-N: 關聯
    // 場景
    // 1. 一個圖書有唯一作者: 1-1
    // 2. 一個作者可以有很多圖書: 1-N
}
