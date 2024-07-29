package com.atguigu.web.biz;

import com.atguigu.web.bean.Person;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 2407009
 * @Description 專門處理 User 有關的業務。
 * @create 2024-07-29
 */
@Component
@Slf4j
public class UserBizHandler {

    /**
     * 查詢指定 id 的用戶。
     * @param request 請求。
     * @return ServerResponse
     */
    public ServerResponse getUser(ServerRequest request) throws Exception {
        String id = request.pathVariable("id");
        log.info("查詢【{}】信息成功，資料庫正在檢索", id);
        // 業務處理
        Person person = new Person(1L, "哈哈", "aa@qq.com", 18, "admin");
        // 建構響應
        return ServerResponse
                .ok()
                .body(person);
    }

    /**
     * 獲取所有用戶。
     * @param request 請求。
     * @return ServerResponse
     */
    public ServerResponse getUsers(ServerRequest request) throws Exception {
        log.info("查詢所有用戶信息成功");
        // 業務處理
        List<Person> list = Arrays.asList(new Person(1L, "哈哈", "aa@qq.com", 18, "admin"),
                                          new Person(2L, "哈哈2", "aa2@qq.com", 12, "admin2"));
        // 建構響應
        return ServerResponse
                .ok()
                .body(list); // 凡是 body 中的物件，就是以前 @ResponseBody 原理。利用 HttpMessageConverter 寫出為 json
    }

    /**
     * 保存用戶。
     * @param request 請求。
     * @return ServerResponse
     */
    public ServerResponse saveUser(ServerRequest request) throws ServletException, IOException {
        // 提取請求體
        Person body = request.body(Person.class);
        log.info("保存用戶信息完成 {}", body);
        return ServerResponse.ok().build();
    }

    /**
     * 更新用戶。
     * @param request 請求。
     * @return ServerResponse
     */
    public ServerResponse updateUser(ServerRequest request) throws ServletException, IOException {
        // 提取請求體
        Person body = request.body(Person.class);
        log.info("更新用戶信息成功: {}", body);
        return ServerResponse.ok().build();
    }

    /**
     * 刪除用戶。
     * @param request 請求。
     * @return ServerResponse
     */
    public ServerResponse deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");
        log.info("刪除【{}】用戶信息成功", id);
        return ServerResponse.ok().build();
    }
}
