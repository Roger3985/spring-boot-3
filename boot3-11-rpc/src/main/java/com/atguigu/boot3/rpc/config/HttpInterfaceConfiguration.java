package com.atguigu.boot3.rpc.config;

import com.atguigu.boot3.rpc.service.ExpressApi;
import com.atguigu.boot3.rpc.service.WeatherInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
@Configuration
public class HttpInterfaceConfiguration {

    @Bean
    HttpServiceProxyFactory httpServiceProxyFactory(@Value("${api.authorization.appcode}") String appCode) { // 已經將授權碼統一放到 application.properties/yaml 進行管理，利用 @Value(${指定名稱}) 利用指定名稱的方式到配置文件中拿
        // 1. 創建客戶端
        WebClient client = WebClient.builder()
                .defaultHeader("Authorization", "APPCODE " + appCode) // 目前還沒有拿到授權碼
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer
                            .defaultCodecs()
                            .maxInMemorySize(256*1024*1024);
                    // 響應資料量太大有可能會超出 BufferSize，所以這裡設置的大一點
                })
                .build();

        // 2. 創建工廠並將 WebClient 放進去
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client)).build();

        return factory;
    }

    @Bean
    WeatherInterface weatherInterface(HttpServiceProxyFactory httpServiceProxyFactory) {

        // 3. 獲取代理物件
        WeatherInterface weatherInterface = httpServiceProxyFactory.createClient(WeatherInterface.class);

        return weatherInterface;
    }

    @Bean
    ExpressApi expressApi(HttpServiceProxyFactory httpServiceProxyFactory) {
        // 獲取代理物件
        ExpressApi expressApi = httpServiceProxyFactory.createClient(ExpressApi.class);
        return expressApi;
    }
}
