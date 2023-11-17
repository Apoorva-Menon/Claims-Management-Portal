package com.policy.microservice.exception;

public class ExpiredPolicyException extends RuntimeException{
	
	public ExpiredPolicyException(String msg)
	{
		super(msg);
	}

}
