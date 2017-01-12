package com.customer.response;

public class CustomerResponse {
	private String message ;
	
	public CustomerResponse(){
	}
	
	public CustomerResponse(String message){
		this.message = message ;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
