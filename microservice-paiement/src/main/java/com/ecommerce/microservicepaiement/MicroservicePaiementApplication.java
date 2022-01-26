package com.ecommerce.microservicepaiement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservicePaiementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePaiementApplication.class, args);
	}

}
