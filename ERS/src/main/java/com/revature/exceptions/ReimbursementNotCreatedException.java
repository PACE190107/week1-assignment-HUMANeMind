package com.revature.exceptions;

public class ReimbursementNotCreatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReimbursementNotCreatedException() {
		super("This reimbursement could not be created.");
	}
}
