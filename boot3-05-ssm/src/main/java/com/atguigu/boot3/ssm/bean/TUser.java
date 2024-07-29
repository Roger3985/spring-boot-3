package com.atguigu.boot3.ssm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TUser {
    private Integer id;
    private String loginName;
    private String nickName;
    private String passwd;
}
