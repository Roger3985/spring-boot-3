package com.atguigu.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

/**
 * @author Roger
 * @Description
 * @create 2024-08-08
 */
@Table("t_author")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TBook {
    @Id
    private Long id;
    private String title;
    private Long authorId;
    private Instant publishTime; // 響應式中日期的映射使用 Instant 或者 LocalXxx
}
