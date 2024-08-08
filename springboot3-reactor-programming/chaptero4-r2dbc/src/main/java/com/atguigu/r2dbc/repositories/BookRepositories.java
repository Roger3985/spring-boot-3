package com.atguigu.r2dbc.repositories;

import com.atguigu.r2dbc.entity.TBook;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Roger
 * @Description
 * @create 2024-08-08
 */
@Repository
public interface BookRepositories extends R2dbcRepository<TBook, Long> {

    @Query("select b.*, t.name as name from t_book b" +
            " LEFT JOIN t_author t on b.author_id = t.id " +
            " WHERE b.id = ?")
    Mono<TBook> findBookAndAuthor(Long bookId);
}
