package com.atguigu.boot3.ssm.controller;

import com.atguigu.boot3.ssm.bean.TUser;
import com.atguigu.boot3.ssm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-29
 */
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    /**
     * 返回 User 的 json 資料。
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public TUser getUser(@PathVariable("id") Integer id) {
        TUser user = userMapper.getUserById(id);
        return user;
    }
}
