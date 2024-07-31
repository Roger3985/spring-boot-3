package com.atguigu.boot.core.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @author 2407009
 * @Description
 * @create 2024-07-31
 */
@Service
public class EventPublisher implements ApplicationEventPublisherAware {

    /**
     * 底層發送事件用的組件，SpringBoot 會通過 ApplicationEventPublisherAware 介面自動注入給我們。
     * 事件是廣播出去的。所有監聽這個事件的監聽器都可以收到。
     */
    ApplicationEventPublisher applicationEventPublisher;

    /**
     * 所有事件都可以發送。
     * @param event
     */
    public void sendEvent(ApplicationEvent event) {
        // 調用底層 API 發送事件
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * 會被自動調用，把真正發事件的底層組件給我們注入進來。
     * @param applicationEventPublisher event publisher to be used by this object。
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
         this.applicationEventPublisher = applicationEventPublisher;
    }
}
