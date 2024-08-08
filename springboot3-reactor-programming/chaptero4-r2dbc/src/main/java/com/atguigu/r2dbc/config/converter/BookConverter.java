package com.atguigu.r2dbc.config.converter;


import com.atguigu.r2dbc.entity.TAuthor;
import com.atguigu.r2dbc.entity.TBook;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Instant;

/**
 * @author Roger
 * @Description
 * @create 2024-08-08
 *
 * 告訴 Spring Data 怎麼封裝 Book 物件
 */
@ReadingConverter // 讀取資料庫資料的時候，把 row 轉成 TBook 把 row 轉成 TBook
public class BookConverter implements Converter<Row, TBook> {

    @Override
    public TBook convert(Row source) {
        // 自定義結果集封裝
        TBook tBook = new TBook();
        tBook.setId(source.get("id", Long.class));
        tBook.setTitle(source.get("title", String.class));
        Long authorId = source.get("author_id", Long.class);
        tBook.setAuthorId(authorId);
        tBook.setPublishTime(source.get("public_time", Instant.class));
        TAuthor tAuthor = new TAuthor();
        tAuthor.setId(authorId);
        tAuthor.setName(source.get("name", String.class));
        tBook.setAuthor(tAuthor);
        return
    }
}
