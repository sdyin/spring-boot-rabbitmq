package com.sdyin.springboot.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void test(){
		System.out.println("提交1");
		System.out.println("提交2");

		System.out.println("------master rebase");
	}
}
