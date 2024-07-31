package com.atguigu.boot.core.controller;

import com.atguigu.boot.core.entity.UserEntity;
import com.atguigu.boot.core.event.EventPublisher;
import com.atguigu.boot.core.event.LoginSuccessEvent;
import com.atguigu.boot.core.service.AccountService;
import com.atguigu.boot.core.service.CouponService;
import com.atguigu.boot.core.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@RestController
public class LoginController {

    @Autowired
    AccountService accountService;

    @Autowired
    CouponService couponService;

    @Autowired
    SysService sysService;

    @Autowired
    EventPublisher eventPublisher;

    /**
     * 增加業務。
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        // 業務處理登入
        System.out.println("業務處理登入完成......");

        //TODO 發送事件
        // 1. 創建事件信息
        LoginSuccessEvent event = new LoginSuccessEvent(new UserEntity(username, password));
        // 2. 發送事件
        eventPublisher.sendEvent(event);

//        // 1. 帳戶服務自動簽到加積分
//        accountService.addAccountScore(username);
//        // 2. 優惠服務隨機發放優惠卷
//        couponService.senaCoupon(username);
//        // 3. 系統服務登記用戶登入信息
//        sysService.recordLog(username);

        // 設計模式: 對新增開放，對修改關閉
        // xxx
        // xxx
        // xxx

        return username + "登入成功";
    }
}
