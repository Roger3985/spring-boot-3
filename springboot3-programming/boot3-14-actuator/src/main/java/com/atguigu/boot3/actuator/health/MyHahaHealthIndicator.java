package com.atguigu.boot3.actuator.health;

import com.atguigu.boot3.actuator.component.MyHahaComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author Roger
 * @Description
 * @create 2024-08-01
 *
 * 1. 實現 HealthIndicator 介面來訂製組件的健康狀態（Health）物件返回
 */
@Component
public class MyHahaHealthIndicator extends AbstractHealthIndicator {

    @Autowired
    MyHahaComponent myHahaComponent;

    /**
     * 健康檢查
     * @param builder
     * @throws Exception
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // 自定義檢查方法
        int check = myHahaComponent.check();
        if (check == 1) {
            // 存活
            builder.up()
                    .withDetail("code", "1000")
                    .withDetail("msg", "活得很健康")
                    .withDetail("data", "我的名字叫haha")
                    .build();
        } else {
            // 下線
            builder.down()
                    .withDetail("code", "1001")
                    .withDetail("msg", "死得很健康")
                    .withDetail("data", "我的名字叫haha完蛋")
                    .build();
        }

    }
}
