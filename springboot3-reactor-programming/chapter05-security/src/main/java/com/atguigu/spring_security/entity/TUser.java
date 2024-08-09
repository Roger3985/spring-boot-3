package com.atguigu.spring_security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-09
 */
@Table(name = "t_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TUser {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Instant createTime;
    private Instant updateTime;
}
