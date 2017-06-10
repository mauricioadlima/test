package com.nfespy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableAutoConfiguration
public class NfeSpyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NfeSpyApplication.class, args);
	}
}
