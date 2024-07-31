package com.atguigu.boot3.features;

import com.atguig.boot3.starter.robot.RobotAutoConfiguration;
import com.atguig.boot3.starter.robot.annotation.EnableRobot;
import com.atguigu.boot3.features.bean.Cat;
import com.atguigu.boot3.features.bean.Dog;
import com.atguigu.boot3.features.bean.Pig;
import com.atguigu.boot3.features.bean.Sheep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * 1. 標示環境
 * 	  (1) 區分出幾個環境： dev（開發環境）、test（測試環境）、prod（生產環境）
 * 	  (2) 指定每個組件在哪個環境下生效; default 環境：默認環境。
 * 	      -> 通過: @Profile({"test"}) 等標註。
 * 	      -> 組件沒有標註 @Profile 代表任意時候都生效。
 * 	  (3) 默認只有激活指定的環境，這些組件才會生效。
 * 2. 激活環境
 * 	  配置文件激活：spring.profiles.active=dev。
 * 	  命令行激活：java -jar xxx.jar --spring.profiles.active=dev (選 Program arguments)。
 * 3. 配置文件怎麼使用 Profile 功能
 *    (1) application.properties: 主配置文件。任何任何情況下都生效。
 *    (2) 其他 Profile 環境下命名規範: application-{profile標示}.properties/yaml
 *        ex: application-dev.properties/yaml
 *    (3) 激活指定環境即可: 配置文件激活、命令行激活。
 *    (4) 效果：
 *        項目的所有生效配置項 = 激活環境配置文件的所有項 + 主配置文件和激活文件不衝突的所有項。
 *        如果發生了配置衝突，以激活的環境配置文件為準。
 *        application-{profile標示}.properties 優先級 application.properties
 *        * 主配置和激活的配置都生效，優先以激活的配置為準。
 */

// SpringBoot 的默認掃描規則，只掃描自己主程序所在的包及其子包

/**
 * 自定義 starter 所有組件包名: com.atguigu.boot3.starter.robot
 * 當前項目的主程序所在包:       com.atguigu.boot3.features
 */
//@Import(RobotAutoConfiguration.class)
@EnableRobot
@Slf4j
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
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.main(Boot306FeaturesApplication.class)
				.sources(Boot306FeaturesApplication.class)
				.bannerMode(Banner.Mode.CONSOLE)
				.properties("server.port=8888", "aaa=bbb") // springboot 所有配置項都可以在這裡定義
//				.environment(null)
//				.listeners(null)
				.run(args);

		try {
			Cat cat = context.getBean(Cat.class);
			log.info("組件cat:{}", cat);
		} catch (Exception e) {
		}

		try {
			Dog dog = context.getBean(Dog.class);
			log.info("組件dog:{}", dog);
		} catch (Exception e) {
		}

		try {
			Pig pig = context.getBean(Pig.class);
			log.info("組件pig:{}", pig);
		} catch (Exception e) {
		}

		try {
			Sheep sheep = context.getBean(Sheep.class);
			log.info("組件sheep:{}", sheep);
		} catch (Exception e) {
		}
	}

}
