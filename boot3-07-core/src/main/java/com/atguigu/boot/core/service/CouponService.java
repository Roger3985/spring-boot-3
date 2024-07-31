package com.atguigu.boot.core.service;

import com.atguigu.boot.core.entity.UserEntity;
import com.atguigu.boot.core.event.LoginSuccessEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@Service
public class CouponService {

    public CouponService() {
        System.out.println("建構器被調用");
    }

    @Async
    @Order(1)
    @EventListener
    public void onEvent(LoginSuccessEvent loginSuccessEvent) {
        System.out.println("=========== CouponService ====== 感知到事件" + loginSuccessEvent);
        UserEntity source = (UserEntity) loginSuccessEvent.getSource();
        senaCoupon(source.getUsername());
    }

    public void senaCoupon(String username) {
        System.out.println(username + "隨機得到了一張優惠卷");
    }
}
