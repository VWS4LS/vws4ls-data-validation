package com.example.restservice;

import org.junit.rules.ExternalResource;

public class DummySemanticHubResource extends ExternalResource {
	
	 @Override
	    protected void before() throws Throwable {
	        // code to set up a specific external resource.
	    };
	    
	    @Override
	    protected void after() {
	        // code to tear down the external resource
	    };

}
