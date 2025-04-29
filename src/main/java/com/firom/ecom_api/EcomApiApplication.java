package com.firom.ecom_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@EnableConfigurationProperties
@SpringBootApplication
public class EcomApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomApiApplication.class, args);
	}

}
