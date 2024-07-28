package com.atguigu.boot.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author Roger
 * @Description
 * @create 2024-07-28
 */
@Data
public class Child {
    private String name;
    private Integer age;
    private Date birthDay;
    private List<String> text;
}
