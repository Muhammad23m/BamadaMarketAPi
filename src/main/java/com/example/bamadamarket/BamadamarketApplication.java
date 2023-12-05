package com.example.bamadamarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BamadamarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BamadamarketApplication.class, args);
	}

}
