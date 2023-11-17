package com.policy.microservice.exception;

public class InvalidMemberIdException extends RuntimeException{
	
	public InvalidMemberIdException(String msg) {
		super(msg);
	}

}
