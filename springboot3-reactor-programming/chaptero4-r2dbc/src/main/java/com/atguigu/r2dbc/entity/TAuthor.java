package com.atguigu.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-07
 */
@Table("t_author")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  TAuthor {
    private Long id;
    private String name;

    // 1-N 如何封裝
    // 臨時字段，並不是資料庫表中的一個字段
    @Transient
    private List<TBook> books;
}
