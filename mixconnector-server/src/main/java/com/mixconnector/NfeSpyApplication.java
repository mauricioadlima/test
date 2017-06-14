package com.mixconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class NfeSpyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NfeSpyApplication.class, args);
	}
}
