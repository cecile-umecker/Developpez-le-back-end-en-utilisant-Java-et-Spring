package com.rentalapi.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Rental API.
 * This class serves as the entry point for the Spring Boot application.
 * 
 * @SpringBootApplication Indicates a configuration class that declares one or more @Bean methods
 * and also triggers auto-configuration and component scanning.
 * The scanBasePackages attribute specifies the base packages to scan for Spring components.
 */

@SpringBootApplication(scanBasePackages = "com.rentalapi.api")
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
