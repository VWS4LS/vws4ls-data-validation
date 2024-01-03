package org.arena.restservice.components.schemavalidator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.networknt.schema.ValidationMessage;

public interface IJsonSchemaValidator {
	
	public Set<ValidationMessage> validateJsonObject(InputStream schema, InputStream jsonObject) throws IOException;

}
