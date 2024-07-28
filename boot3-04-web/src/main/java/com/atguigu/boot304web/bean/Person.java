package com.atguigu.boot304web.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Roger
 * @Description
 * @create 2024-07-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement // 可以寫出為 xml 文檔
public class Person {
    private Long id;
    private String username;
    private String email;
    private Integer age;
}
