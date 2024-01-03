package org.arena.restservice.components.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static String serialization(ServiceResult serviceResult) throws JsonProcessingException {
		return objectMapper.writeValueAsString(serviceResult);
	}

}
