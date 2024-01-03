package org.arena.restservice.components.serialization;

public class ServiceResult {
	private boolean resultValue;
	private String valueType = "boolean";
	private String message;
	
	
	public ServiceResult(boolean resultValue, String message) {
		super();
		this.resultValue = resultValue;
		this.message = message;
	}
	public boolean isResultValue() {
		return resultValue;
	}
	public void setResultValue(boolean resultValue) {
		this.resultValue = resultValue;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
