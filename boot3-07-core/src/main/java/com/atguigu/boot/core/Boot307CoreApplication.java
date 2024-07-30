package com.atguigu.boot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // SPI
public class Boot307CoreApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Boot307CoreApplication.class);

		// 參數設置
		// application.addInitializers();

		application.run(args);

		// SpringApplication.run(Boot307CoreApplication.class, args);
	}

}
