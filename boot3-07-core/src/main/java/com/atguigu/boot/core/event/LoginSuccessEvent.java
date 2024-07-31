package com.atguigu.boot.core.event;

import com.atguigu.boot.core.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author 2407009
 * @Description 登入成功事件，所有事件都推薦繼承 ApplicationEvent。
 * @create 2024-07-31
 */
public class LoginSuccessEvent extends ApplicationEvent {

    /**
     *
     * @param source 代表是誰登入成功了
     */
    public LoginSuccessEvent(UserEntity source) {
        super(source);
    }
}
