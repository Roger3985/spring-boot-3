package com.atguigu.boot3.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * KafkaAutoConfiguration 提供如下的功能
 * 1. KafkaProperties: kafka 的所有配置; 以 spring.kafka 開始
 * 	  - bootstrapServers: kafka 集群中的所有服務器地址
 * 	  - properties: 參數設置
 * 	  - consumer: 消費者
 * 	  - producer: 生產者
 * 	  ......
 * 2. @EnableKafka: 開啟 Kafka 的註解驅動功能
 * 3. KafkaTemplate: 收發消息(消息的增刪改查)......
 * 4. KafkaAdmin: 維護主題等(主題的增刪改查)......
 * 5. @EnableKafka + @KafkaListener 接收消息
 *    1) 消費者來接受消息，需要有 group-id
 *    2) 收消息使用 @KafkaListener + ConsumerRecord
 *    3) spring kafka 的所有配置
 */
@EnableKafka
@SpringBootApplication
public class Boot312MessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(Boot312MessageApplication.class, args);
	}

}
