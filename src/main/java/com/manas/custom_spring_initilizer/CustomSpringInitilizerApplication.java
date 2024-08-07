package com.manas.custom_spring_initilizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CustomSpringInitilizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomSpringInitilizerApplication.class, args);
	}

}
