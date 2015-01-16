package com.ucab.servimovil.model;

public class LDAPObject {
	
	private String result;
	private String message;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "LDAPObject [result=" + result + ", message=" + message + "]";
	}
	
	

}
