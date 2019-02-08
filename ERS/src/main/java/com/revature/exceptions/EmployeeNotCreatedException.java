package com.revature.exceptions;

public class EmployeeNotCreatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotCreatedException(String name) {
		super("Employee [" + name + "] could not be created.");
	}
}
