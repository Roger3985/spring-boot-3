package com.atguigu.web.config;

import com.atguigu.web.biz.UserBizHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-29
 */

/**
 * 場景：User RESTful - CRUD
 * ● GET /user/1        獲取1號用戶
 * ● GET /users         獲取所有用戶
 * ● POST /user         請求體攜帶JSON，新增一個用戶
 * ● PUT /user/1        請求體攜帶JSON，修改1號用戶
 * ● DELETE /user/1     刪除1號用戶
 */
@Configuration
public class WebFunctionConfig {

    /**
     * 函數式 Web
     * 1. 給容器中放一個 Bean:類型是 RouterFunction<ServerResponse>，集中所以路由信息。
     * 2. 每個業務準備一個自己的 Handler
     *
     * 核心四大物件
     * 1. RouterFunction； 定義路由信息。發什麼請求，誰來處理。
     * 2. RequestPredicate； 定義請求； 請求謂語。請求方式(GET、POST)、請求參數。
     * 3. ServerRequest； 封裝請求完整資料。
     * 4. ServerResponse； 封裝響應完整資料。
     */
    @Bean
    public RouterFunction<ServerResponse> userRoute(UserBizHandler userBizHandler /* 這個物件會被自動注入進來 */) {
        return RouterFunctions.route() // 開始定義路由信息
                .GET("/user/{id}", RequestPredicates.accept(MediaType.ALL), userBizHandler::getUser)
                .GET("/users", userBizHandler::getUsers)
                .POST("/user", RequestPredicates.accept(MediaType.APPLICATION_JSON), userBizHandler::saveUser)
                .PUT("/user/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), userBizHandler::updateUser)
                .DELETE("/user/{id}", userBizHandler::deleteUser)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> groupRoute(UserBizHandler userBizHandler /* 這個物件會被自動注入進來 */) {
        return RouterFunctions.route() // 開始定義路由信息
                .GET("/user/{id}", RequestPredicates.accept(MediaType.ALL)
                        .and(RequestPredicates.param("aa", "bb")), userBizHandler::getUser)
                .GET("/users", userBizHandler::getUsers)
                .POST("/user", RequestPredicates.accept(MediaType.APPLICATION_JSON), userBizHandler::saveUser)
                .PUT("/user/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), userBizHandler::updateUser)
                .DELETE("/user/{id}", userBizHandler::deleteUser)
                .build();
    }

}
