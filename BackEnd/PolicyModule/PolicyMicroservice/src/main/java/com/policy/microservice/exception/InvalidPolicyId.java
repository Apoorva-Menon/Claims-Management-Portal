package com.policy.microservice.exception;


public class InvalidPolicyId extends RuntimeException{
	
	public InvalidPolicyId(String msg) {
		super(msg);
	}

}
