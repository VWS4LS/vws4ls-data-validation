package org.arena.restservice.components.semantichubhandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.arena.restservice.components.BaSyxHttpClientUtils;
import org.arena.restservice.exceptions.SemanticIdDoesNotExistException;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.DeserializationException;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.json.JsonDeserializer;
import org.eclipse.digitaltwin.aas4j.v3.model.Property;
import org.eclipse.digitaltwin.aas4j.v3.model.Submodel;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelElement;
import org.eclipse.digitaltwin.basyx.http.Base64UrlEncodedIdentifier;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultSemanticIdHandler implements ISemanticIdHandler {
	private static final String SUBMODEL_ELEMENT_ID = "InformationDescriptionSetJsonSchema.JsonSchema";
	private static final String JSON_FIELD_RESULT = "result";	
	
	@Value("${semantichub.address}")
	private String url;
	private JsonDeserializer deserializer = new JsonDeserializer();
	

	@Override
	public String getJsonSchemaSubmodelElement(String semanticId) throws IOException, ParseException, DeserializationException{
		List<Submodel> submodels = getAllSubmodels();
		Optional<Submodel> submodelOptional = submodels.stream().filter(sm-> getReferenceId(sm).equals(semanticId)).findAny();
		
		if(submodelOptional.isEmpty())
			throw new SemanticIdDoesNotExistException(semanticId);
		
		return getJsonSchema(submodelOptional.get());
	}
	
	private String getJsonSchema(Submodel submodel) throws IOException, ParseException, DeserializationException {
		String submodelId = submodel.getId();
		CloseableHttpResponse response;
		response = BaSyxHttpClientUtils.executeGetOnURL(createSpecificSubmodelElementURL(url, submodelId, SUBMODEL_ELEMENT_ID));
		SubmodelElement submodelElement = deserializer.readReferable(BaSyxHttpClientUtils.getResponseAsString(response), SubmodelElement.class);
		return ((Property)submodelElement).getValue();
	}

	private List<Submodel> getAllSubmodels() throws IOException, ParseException, DeserializationException {
		CloseableHttpResponse response;
		response = BaSyxHttpClientUtils.executeGetOnURL(url);
		List<Submodel> submodels = deserializer.readReferables(removePagingPart(BaSyxHttpClientUtils.getResponseAsString(response)), Submodel.class);
		response.close();
		return submodels;
	}
	
	private JsonNode removePagingPart(String schema) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode actualObj = mapper.readTree(schema);
		return actualObj.get(JSON_FIELD_RESULT);
	}
	
	private String getReferenceId(Submodel submodel) {
		return submodel.getSemanticId().getKeys().get(0).getValue();
	}

	
	private String createSpecificSubmodelElementURL(String url, String submodelId, String smeIdShort) {
		return url + "/" + Base64UrlEncodedIdentifier.encodeIdentifier(submodelId)+ "/submodel-elements/" + smeIdShort;
	}
	
	
	public static String getSpecificSubmodelAccessPath(String submodelRepoURL, String submodelId) {
		return submodelRepoURL + "/" + Base64UrlEncodedIdentifier.encodeIdentifier(submodelId);
	}
	
	protected String getSpecificAasAccessURL(String aasId) {
		return url + "/" + Base64UrlEncodedIdentifier.encodeIdentifier(aasId);
	}

	@Override
	public InputStream fetchJsonSchemaFromSemanticHub(String semanticId) {
		return getClass().getClassLoader().getResourceAsStream("JsonSchema4ExampleDataValidationV2.json");
	}

}
