package com.atguigu.boot3.actuator.component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/**
 * @author Roger
 * @Description
 * @create 2024-08-01
 */
@Component
public class MyHahaComponent {

    Counter counter = null;

    /**
     * 注入 meterRegistry 來保存和統計所有指標。
     * @param meterRegistry
     */
    public MyHahaComponent(MeterRegistry meterRegistry)  {
        // 得到一個名叫 myhaha.hello 的計數器
        counter = meterRegistry.counter("myhaha.hello");
    }

    public int check() {
        // 業務狀態判斷這個組件是否該是存活狀態
        return 1;
    }

    public void hello() {
        System.out.println("hello");
        counter.increment();
    }
}
