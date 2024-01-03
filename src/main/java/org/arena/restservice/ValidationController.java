package org.arena.restservice;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.hc.core5.http.ParseException;
import org.arena.restservice.components.schemavalidator.IJsonSchemaValidator;
import org.arena.restservice.components.semantichubhandler.ISemanticIdHandler;
import org.arena.restservice.components.serialization.JsonHelper;
import org.arena.restservice.components.serialization.ServiceResult;
import org.arena.restservice.exceptions.SemanticIdDoesNotExistException;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.DeserializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.networknt.schema.ValidationMessage;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

/**
 * Rest controller for the validation service.
 * 
 * @author zhangzai
 *
 */
@RestController
@RequestMapping("/arena/validation")
public class ValidationController {
	private IJsonSchemaValidator validator;
	private ISemanticIdHandler handler;
	
	@Autowired
	public ValidationController(IJsonSchemaValidator validator, ISemanticIdHandler handler) {
		this.validator = validator;
		this.handler = handler;
	}
	
	/**
	 * The validation service that performs the Json schema validation
	 * @param semanticId: semantic Id of the submodel
	 * @param file: the Json file which need to be validated
	 * @return The validation result indicating the results
	 */
	@RequestMapping(value = "/{semantic-id}/attachment", consumes = {"multipart/form-data"}, method = RequestMethod.PATCH)
	public ResponseEntity<String> validate(
			@Parameter(in = ParameterIn.PATH, description = "semantic id", required = true) @PathVariable("semantic-id") String semanticId,
			@Parameter(description = "file detail") @RequestPart("file") MultipartFile file){
		
		try {
			// Find the right shell with semantic Id, return the Json schema
			String schema = handler.getJsonSchemaSubmodelElement(semanticId);
			
			// Validate the json schema
			Set<ValidationMessage> messages = validator.validateJsonObject(new ByteArrayInputStream(schema.getBytes()), file.getInputStream());
			
			ServiceResult result = createValidationResult(messages);
			
			return ResponseEntity.ok(JsonHelper.serialization(result));
		} catch (IOException | ParseException | DeserializationException  e) {
			return new ResponseEntity<String>("Error happens during processing the request.", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}catch(SemanticIdDoesNotExistException e) {
			return new ResponseEntity<String>("The semantic id "+ semanticId +" is not found.", HttpStatus.NOT_FOUND);
		} 
	}

	private ServiceResult createValidationResult(Set<ValidationMessage> messages) {
		if(messages.isEmpty()) {
			return new ServiceResult(true, "");
		}
		return new ServiceResult(false, generateErrorMessage(messages));
	}

	private String generateErrorMessage(Set<ValidationMessage> messages) {
		String errorMsg = "";
		for(ValidationMessage msg : messages) {
			errorMsg = errorMsg + msg.getMessage() + " ";
		}
		return errorMsg;
	}
}
