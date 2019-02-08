package com.revature.exceptions;

public class CredentialsNotValid extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CredentialsNotValid() {
		super("The Username or Password entered doesn't match our records");
	}
}
