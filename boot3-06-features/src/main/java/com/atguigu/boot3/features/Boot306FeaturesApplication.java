package com.atguigu.boot3.features;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication // 主程序類
public class Boot306FeaturesApplication {

	public static void main(String[] args) {
//		// 1. SpringApplication: Boot 應用的核心 API 入口
//		// SpringApplication.run(Boot306FeaturesApplication.class, args);
//
//		// 1. 自定義 SpringApplication 的底層設置
//		SpringApplication application = new SpringApplication(Boot306FeaturesApplication.class);
//
//		// 程序化調整 [SpringApplication 的參數]
//		// application.setDefaultProperties();
//		// 這個配置不優先
//		application.setBannerMode(Banner.Mode.CONSOLE);
//
//		// [配置文件優先級高於程序化調整的優先級]
//
//		// 2. SpringApplication 運行起來
//		application.run(args);

		// 2. Builder 方式構建 SpringApplication; 通過 FluentAPI 進行設置
		new SpringApplicationBuilder()
				.main(Boot306FeaturesApplication.class)
				.sources(Boot306FeaturesApplication.class)
				.bannerMode(Banner.Mode.CONSOLE)
//				.environment(null)
//				.listeners(null)
				.run(args);
	}

}
