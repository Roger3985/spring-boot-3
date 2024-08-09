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
@Table(name = "t_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TUserRole {
    @Id
    private Long id;
    private Long userId;
    private Long roleId;
    private Instant createTime;
    private Instant updateTime;
}
