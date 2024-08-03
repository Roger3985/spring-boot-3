package com.atguigu.boot3.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合 Prometheus + Grafana 完成線上應用指標監控系統
 * 1. 改造 SpringBoot 應用，產生 Prometheus 需要的格式資料
 *    - 導入 micrometer-registry-prometheus
 * 2. 部屬 java 應用。在同一個機器內，訪問 http:ip:9999/actuator/prometheus 就能獲得資料
 *    在外部訪問 http:ip:9999/actuator/prometheus
 */
@SpringBootApplication
public class Boot314ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot314ActuatorApplication.class, args);
    }

}
