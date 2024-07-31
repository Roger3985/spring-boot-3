package com.atguigu.boot.core;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableAsync // 開啟異步
//@EnableScheduling // 開啟定時任務
@SpringBootApplication // SPI
public class Boot307CoreApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Boot307CoreApplication.class);

		// 參數設置
		// application.addInitializers();

		application.run(args);

		// SpringApplication.run(Boot307CoreApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
			System.out.println("====ApplicationRunner 運行了......");
		};
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			System.out.println("==============CommandLineRunner 運行了.......");
		};
	}
}
