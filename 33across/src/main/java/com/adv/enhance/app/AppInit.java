package com.adv.enhance.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.adv.enhance.api", "com.adv.enhance.base", "com.adv.enhance.engine", "com.adv.enhance.web"})
public class AppInit {


	public static void main(String[] args) {
		SpringApplication.run(AppInit.class, args);
	}

}



