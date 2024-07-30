package com.atguigu.boot3.features;

import com.atguigu.boot3.features.service.HelloService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

// 測試類也必須在主程序所在的包及其子包
@SpringBootTest // 具備測試 SpringBoot 應用容器中所有組件的功能
class Boot306FeaturesApplicationTests {

	@Autowired // 自動注入任意組件即可測試
	HelloService helloService;

	@DisplayName("測試一")
	@Test
	void contextLoads() {
		int sum = helloService.sum(1, 2);
		System.out.println(sum);
	}

	@DisplayName("測試二")
	@Test
	void test01() {
		System.out.println("aaa");
	}

	@BeforeAll // 所有方法運行之前先運行這個 : 只打印一次
	static void initAll() {
		System.out.println("hello");
	}

	@BeforeEach // 每個方法運行之前先運行這個 : 每個方法打印一次
	void init() {
		System.out.println("world");
	}
}
