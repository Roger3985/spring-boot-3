package com.atguigu.boot.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private String username;
    private String password;
}
