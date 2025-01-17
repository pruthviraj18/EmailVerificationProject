package com.application.emailproject.exception;

public class UserAlreadyExistsException extends RuntimeException {

	//If the user already exists in the database unable to create a user with the same username
	public UserAlreadyExistsException(String message)
	{
		super(message);
	}
}
