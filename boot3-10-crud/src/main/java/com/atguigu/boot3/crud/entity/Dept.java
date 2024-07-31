package com.atguigu.boot3.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept {
    private Long id;
    private String deptName;
}
