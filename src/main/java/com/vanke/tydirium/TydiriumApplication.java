package com.vanke.tydirium;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 
 * 
 * @Description: SpringBoot启动类
 *
 * @author: songjia 
 * @date: 2017年8月23日 下午4:25:48
 */
@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.vanke.tydirium.mapper")
public class TydiriumApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TydiriumApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TydiriumApplication.class, args);
	}
}
