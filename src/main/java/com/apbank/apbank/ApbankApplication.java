package com.apbank.apbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ApbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApbankApplication.class, args);
	}

}
