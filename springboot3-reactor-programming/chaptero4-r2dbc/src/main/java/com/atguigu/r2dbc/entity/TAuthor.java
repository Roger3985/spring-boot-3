package com.atguigu.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-07
 */
@Table("t_author")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TAuthor {
    private Long id;
    private String name;
}
