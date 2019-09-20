package com.huxin.sboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//扫描mapper接口
@MapperScan("com.huxin.sboot.dao")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
