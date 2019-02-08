package com.revature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImplementation;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementServiceImplementation;

public class MasterDispatcher {

	private MasterDispatcher() {}
	
	private static final EmployeeService employeeService = EmployeeServiceImplementation.getEmployeeService();
	private static final ReimbursementService reimbursementService = ReimbursementServiceImplementation.getReimbursementService();
	
	public static Object process(HttpServletRequest request, HttpServletResponse response) {
		if (request.getRequestURI().contains("employee")) {
			return employeeService.process(request, response);}
		else if (request.getRequestURI().contains("manager")) {
			return reimbursementService.process(request, response);}
		else if (request.getRequestURI().contains("login")) {
			return employeeService.process(request, response);}
		else 
			return "Not yet implemented";
	}
}