package com.example.restservice;


import org.eclipse.digitaltwin.basyx.aasenvironment.component.AasEnvironmentComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A dummy semantic hub that is used for test purpose.
 * 
 * @author zhangzai
 *
 */
@SpringBootApplication
public class DummySemanticHubEnvironment {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AasEnvironmentComponent.class);
		app.run(args);
	}

}
