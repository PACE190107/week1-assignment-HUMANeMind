package com.revature.exceptions;

public class ReimbursementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReimbursementNotFoundException(int id) {
		super("Employee with id [" + id + "] could not be found");
	}
}
