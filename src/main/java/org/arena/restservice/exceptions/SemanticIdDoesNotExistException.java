package org.arena.restservice.exceptions;

@SuppressWarnings("serial")
public class SemanticIdDoesNotExistException extends RuntimeException {
	
	public SemanticIdDoesNotExistException(String semanticId) {
		super("Semantic Id "+ semanticId+ " does not exist");
	}

}
