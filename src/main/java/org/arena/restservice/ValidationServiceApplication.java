package org.arena.restservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValidationServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ValidationServiceApplication.class);
		app.run(args);
	}

}
