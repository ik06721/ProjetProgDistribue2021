package com.ecommerce.registre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistreApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistreApplication.class, args);
	}

}
