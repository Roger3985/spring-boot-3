package com.atguigu.boot3.message;

import com.atguigu.boot3.message.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class Boot312MessageApplicationTests {

	@Autowired
	KafkaTemplate kafkaTemplate;

	@Test
	void contextLoads() {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();
		CompletableFuture[] futures = new CompletableFuture[10000];

		for (int i = 0; i < 10000; i++) {
			// JUC
			CompletableFuture future = kafkaTemplate.send("news", "haha", "哈哈哈");
			futures[i] = future;
		}

		CompletableFuture.allOf(futures)
				.join();

		stopWatch.stop();

		long totalTimeMillis = stopWatch.getTotalTimeMillis();
		System.out.println("10000 消息發送完成: 時間: " + totalTimeMillis);
	}

	@Test
	void send() {
		CompletableFuture future = kafkaTemplate.send("newshaha", "person", new Person(1, "Roger", "aa@gmail.com"));
		future.join();
		System.out.println("發送消息成功...");
	}

}
