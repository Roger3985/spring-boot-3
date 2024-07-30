package com.atguigu.boot.core.listener;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

/**
 * @author Roger
 * @Description SpringBoot 應用生命週期監聽
 * @create 2024-07-30
 */

/**
 * Listener 先要從 META-INF/spring.factories 讀到
 *
 * 1. 引導：利用 BootstrapContext 引導整個項目啟動
 *         starting:            應用開始，SpringApplication 的 run 方法一調用，只要有了 BootstrapContext 就執行
 *         environmentPrepared: 環境準備好（把項目參數等綁定到環境變量中），但是 ioc 還沒有創建; [調用一次]
 * 2. 啟動
 *         contextPrepared:     ioc 容器創建並準備好，但是 sources（主配置類）沒加載。並關閉引導上下文; 組件都沒創建 [調用一次]
 *         contextLoaded:       ioc 容器加載。主配置類加載進去了。但是 ioc 容器還沒刷新（我們的 bean 沒創建）。
 *         =============截止以前，ioc 容器裡面還沒造 bean 呢================================================
 *         started:             ioc 容器刷新了（所有 bean 造好了），但是 runner 沒調用。
 *         ready:               ioc 容器刷新了（所有 bean 造好了），所有 runner 調用完成。
 * 3. 運行
 *         以前步驟都正確執行，代表容器 running。
 */
public class MyAppListener implements SpringApplicationRunListener {
    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        System.out.println("========starting=======正在啟動========");
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        System.out.println("========environmentPrepared=======環境準備完成========");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("========contextPrepared=======ioc容器準備完成========");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("========contextLoaded=======ioc容器加載完成========");
    }

    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        System.out.println("========started=======啟動完成========");
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        System.out.println("========ready=======準備就緒========");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("========failed=======應用啟用失敗========");
    }
}
