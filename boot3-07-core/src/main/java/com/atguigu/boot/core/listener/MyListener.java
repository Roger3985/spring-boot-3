package com.atguigu.boot.core.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Roger
 * @Description
 * @create 2024-07-31
 */
public class MyListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("========事件=======到達============="  + event);
    }
}
