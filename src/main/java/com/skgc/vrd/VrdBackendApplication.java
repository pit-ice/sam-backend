package com.skgc.vrd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching  
@SpringBootApplication
public class VrdBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrdBackendApplication.class, args);
	}

}
