package com.abner.financialtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.abner.financialtracker.model")
@EnableJpaRepositories(basePackages = "com.abner.financialtracker.repository")
public class FinancialtrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialtrackerApplication.class, args);
	}

}
