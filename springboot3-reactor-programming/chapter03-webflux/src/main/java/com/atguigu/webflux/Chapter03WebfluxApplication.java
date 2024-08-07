package com.atguigu.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

// @EnableWebFlux // 不推薦使用: 此為: 開啟 WebFlux 自定義；禁用 WebFlux 的默認效果，完全自定義
// WebFluxAutoConfiguration 的自動配置會生效
@SpringBootApplication
public class  Chapter03WebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chapter03WebfluxApplication.class, args);
	}

}
