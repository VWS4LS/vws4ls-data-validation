package org.arena.restservice.components.semantichubhandler;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hc.core5.http.ParseException;
import org.eclipse.digitaltwin.aas4j.v3.dataformat.DeserializationException;

public interface ISemanticIdHandler {
	
	public String getJsonSchemaSubmodelElement(String semanticId)throws IOException, ParseException, DeserializationException;
	
	public InputStream fetchJsonSchemaFromSemanticHub(String semanticId);

}
