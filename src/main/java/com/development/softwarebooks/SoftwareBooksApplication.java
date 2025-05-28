package com.development.softwarebooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.development.softwarebooks.config.DiscountProperties;

/**
 * The main class for starting the SoftwareBooks Spring Boot application.
 *
 * @author Swetha Thimmairajan
 * @version 1.0
 * @since 2025-05-22
 */

@SpringBootApplication
@EnableConfigurationProperties(DiscountProperties.class)
public class SoftwareBooksApplication {
	public static void main(String[] args) {
		SpringApplication.run(SoftwareBooksApplication.class, args);
	}
}