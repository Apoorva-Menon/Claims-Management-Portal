package com.mfpe.memberService.exceptions;

public class InvalidMemberIdException extends RuntimeException {

	public InvalidMemberIdException(String message)
	{
		super(message);
	}
}
