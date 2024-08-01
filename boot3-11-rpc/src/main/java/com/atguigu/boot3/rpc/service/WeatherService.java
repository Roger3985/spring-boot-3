package com.atguigu.boot3.rpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 2407009
 * @Description
 * @create 2024-08-01
 */
@Service
public class WeatherService {

    @Autowired
    WeatherInterface weatherInterface;

    public Mono<String> weather(String city) {
        // 遠程調用中央氣象局 API

        // Mono<String> mono = getByWebClient(city);
        // 以下放到都放到 config 中
//        // 1. 創建客戶端
//        WebClient client = WebClient.builder()
//                .baseUrl("https://api.example.com")
//                .codecs(clientCodecConfigurer -> {
//                    clientCodecConfigurer
//                            .defaultCodecs()
//                            .maxInMemorySize(256*1024*1024);
//                    // 響應資料量太大有可能會超出 BufferSize，所以這裡設置的大一點
//                })
//                .build();
//
//        // 2. 創建工廠並將 WebClient 放進去
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builder(WebClientAdapter.forClient(client)).build();
//
//        // 3. 獲取代理物件
//        WeatherInterface weatherInterface = factory.createClient(WeatherInterface.class);

        Mono<String> weather = weatherInterface.getWeather(city);

        return weather;
    }

    private static Mono<String> getByWebClient(String city) {
        // 1. 創建 WebClient
        WebClient client = WebClient.create();

        // 2. 準備資料
        Map<String, String> params = new HashMap<>();
        // params.put("area", "台北");
        params.put("area", city); // 傳入哪些資料(地區)就查詢哪些地區資料。

        // 3. 定義發請求的行為(目前不支持 block 故換成 Mono)
//        String json = client.get()
//                .uri("url?area={area}", params)
//                .accept(MediaType.APPLICATION_JSON) // 定義響應的內容類型
//                .header("Authorization", "內容需要準確拿到相關網站授權碼")
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();

        // 3. 定義發請求的行為(目前不支持 block 故換成 Mono) 類似 JUC 中的 CompletableFuture 類似非同步回調
        Mono<String> mono = client.get()
                .uri("url?area={area}", params)
                .accept(MediaType.APPLICATION_JSON) // 定義響應的內容類型
                .header("Authorization", "內容需要準確拿到相關網站授權碼") // 定義請求頭
                .retrieve()
                .bodyToMono(String.class);

        mono.subscribe(value -> {
            System.out.println(value);
        });
        return mono;
    }
}