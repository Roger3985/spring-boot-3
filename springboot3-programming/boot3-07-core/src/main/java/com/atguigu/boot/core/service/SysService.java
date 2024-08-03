package com.atguigu.boot.core.service;

import com.atguigu.boot.core.entity.UserEntity;
import com.atguigu.boot.core.event.LoginSuccessEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@Service
public class SysService {

    @EventListener
    public void haha(LoginSuccessEvent loginSuccessEvent) {
        System.out.println("========== 感知到了 SysService 事件 ===========" + loginSuccessEvent);
        UserEntity source = (UserEntity) loginSuccessEvent.getSource();
        recordLog(source.getUsername());
    }

    public void recordLog(String username) {
        System.out.println(username + "登入信息已被紀錄");
    }
}
