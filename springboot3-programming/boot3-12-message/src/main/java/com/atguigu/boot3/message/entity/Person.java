package com.atguigu.boot3.message.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer id;
    private String name;
    private String email;
}
