package com.atguigu.boot.core.service;

import com.atguigu.boot.core.event.LoginSuccessEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@Service
public class HahaService {

    @EventListener
    public void onEvent(LoginSuccessEvent loginSuccessEvent) {
        System.out.println("========== HahaService ============ 感知到事件" + loginSuccessEvent);
        // 調用業務
    }
}
