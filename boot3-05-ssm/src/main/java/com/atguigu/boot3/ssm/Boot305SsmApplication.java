package com.atguigu.boot3.ssm;

import com.atguig.boot3.starter.robot.annotation.EnableRobot;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. 告訴 MyBatis【批量掃描註解】，掃描哪個包下面的所有介面。
 * 2. 使用mybatis.mapper-locations，告訴 MyBatis，每個介面的 xml 文件都在哪裡。
 * 3. MyBatis 自動關聯綁定。
 */
@EnableRobot
@MapperScan(basePackages = "com.atguigu.boot3.ssm.mapper")
@SpringBootApplication
public class Boot305SsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(Boot305SsmApplication.class, args);
	}

}
