package com.groupware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.groupware.common.config.DatabaseConfigurator;

@SpringBootApplication
public class GroupwareSystemApplication {

	public static void main(String[] args) {
		// DB接続設定
		DatabaseConfigurator.configureDatabaseProperties();
		
		SpringApplication.run(GroupwareSystemApplication.class, args);
	}
}
