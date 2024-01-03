package org.arena.restservice.components.schemavalidator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.SpecVersion.VersionFlag;

@Component
public class JsonSchemaValidator implements IJsonSchemaValidator {
	
	private JsonSchemaFactory factory;

	public JsonSchemaValidator(@Value("${validation.version:V7}")String version ) {
		factory = JsonSchemaFactory.getInstance(VersionFlag.valueOf(version));
	}
	
	@Override
	public Set<ValidationMessage> validateJsonObject(InputStream schema, InputStream jsonObject) throws IOException {
	    JsonSchema jsonSchema = factory.getSchema(schema);
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode jsonNode = mapper.readTree(jsonObject);
	    return jsonSchema.validate(jsonNode);
	}

}
