package org.arena.restservice;

import org.arena.restservice.components.semantichubhandler.DefaultSemanticIdHandler;
import org.arena.restservice.components.semantichubhandler.ISemanticIdHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ValidationController.class)
public class ValidationServiceConfiguration {
	@Bean
	public ISemanticIdHandler getHandler() {
		return new DefaultSemanticIdHandler();
	}
}
