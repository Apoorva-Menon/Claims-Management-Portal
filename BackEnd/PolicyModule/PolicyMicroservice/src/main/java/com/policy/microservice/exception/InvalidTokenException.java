package com.policy.microservice.exception;

public class InvalidTokenException extends RuntimeException{
	
	public InvalidTokenException(String message) {
		super(message);
	}

}
