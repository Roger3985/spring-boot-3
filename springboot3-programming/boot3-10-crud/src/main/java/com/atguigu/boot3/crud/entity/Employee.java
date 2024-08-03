package com.atguigu.boot3.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Roger
 * @Description
 * @create 2024-08-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String EmployeeName;
    private Integer age;
    private String email;
}
