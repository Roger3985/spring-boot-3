package com.atguigu.boot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Roger
 * @Description
 * @create 2024-07-27
 */
@Data // 自動生成 JavaBean 屬性的 getter / setter
@NoArgsConstructor // 自動生成無參數建構子
@AllArgsConstructor // 自動生成全參數建構子
@Component
@ConfigurationProperties(prefix = "person") // 和配置文件中 person 前綴的所有配置進行綁定
public class Person {
    private String name;
    private String age;
    private Date birthDay;
    private Boolean like;
    private Child child; // 嵌套物件
    private List<Dog> dogs; // 陣列（裡面是物件）
    private Map<String, Cat> cats; // 表示 Map
}
