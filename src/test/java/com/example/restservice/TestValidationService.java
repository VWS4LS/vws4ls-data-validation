package com.example.restservice;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.arena.restservice.ValidationServiceApplication;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class TestValidationService {
	private static final String FILE_PATH = "";
	private static final String SEMANTIC_ID = "semantic1234";
	private static ConfigurableApplicationContext appContext1, appContext2;
	
	@BeforeClass
	public static void startAASRepo() throws Exception {
		System.setProperty("spring.config.name", "semantichubApp");
		appContext2 = new SpringApplication(DummySemanticHubEnvironment.class).run(new String[] {});
		
		SpringApplication app = new SpringApplication(ValidationServiceApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
		appContext1 = app.run();
	}
	
	@Test
	public void testValidationWithTrue() throws IOException {
		HttpPatch patch = createPatchService(FILE_PATH, SEMANTIC_ID);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(patch);
		
		String resosneString  = new String(response.getEntity().getContent().readAllBytes());
		assertTrue(resosneString.equals("True"));
	}
	
	private HttpPatch createPatchService(String filePath, String semanticId) {
		
		HttpPatch patch = new HttpPatch(getURL()+"/arena/validation/"+semanticId+"/attachment");
		
		MultipartEntityBuilder entityBuilder =  MultipartEntityBuilder.create();
		entityBuilder.addPart("file", new FileBody(new File(filePath)));
		entityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);
		
		patch.setEntity(entityBuilder.build());
		return patch;
	}

	private String getURL() {
		return "http://localhost:8080";
	}

	@AfterClass
	public static void shutdownAASRepo() {
		appContext1.close();
		appContext2.close();
	}
}
