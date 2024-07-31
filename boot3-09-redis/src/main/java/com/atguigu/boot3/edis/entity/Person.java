package com.atguigu.boot3.edis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Date birthDay;
}
