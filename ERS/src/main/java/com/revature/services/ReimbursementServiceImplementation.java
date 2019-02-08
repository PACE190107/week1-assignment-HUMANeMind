package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.ReimbursementDaoImplementation;
import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementHistory;
import com.revature.models.Employee;

public class ReimbursementServiceImplementation implements ReimbursementService {

	private static ReimbursementServiceImplementation reimbursementServiceImpl;

	private ReimbursementServiceImplementation() {

	}

	public static ReimbursementServiceImplementation getReimbursementService() {
		if (reimbursementServiceImpl == null) {
			reimbursementServiceImpl = new ReimbursementServiceImplementation();
		}
		return reimbursementServiceImpl;
	}

	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		String[] path = request.getRequestURI().split("/");

		switch (method) {
			case "GET": {
				// GET ALL LOGIC
				if ("employee".equals(path[path.length - 1])) { // execute if the request is /PaceServletExamples/rest/todos
					List<Reimbursement> allReimbursements = getAllReimbursements(1);
					return allReimbursements;
				}
	
				// GET ONE LOGIC
				if ("employee.html".equals(path[path.length - 2])) { // execute if request looks like
																		// /PaceServletExamples/rest/todos/3
					try {
						Reimbursement reimbursementId = new Reimbursement();
						reimbursementId.setId(Integer.valueOf(path[path.length - 1]));
	
						reimbursementId = findReimbursement(reimbursementId);
						System.out.println(reimbursementId);
	
						return reimbursementId;
					} catch (NumberFormatException e) {
						return "Cannot convert " + path[path.length - 1] + " into a number";
					} catch (EmployeeNotFoundException e) {
						return e.getMessage();
					}
				}
				break;
			}
			case "POST": {
	
				break;
			}
	
			case "PUT": {
				break;
			}
	
			case "DELETE": {
				break;
			}
		}

		return null;
	}

	public boolean registerReimbursement(Reimbursement insertedReimbursement) {
		return ReimbursementDaoImplementation.getReimbursementDao().insertReimbursement(insertedReimbursement);
	}

	public boolean modifyReimbursement(Reimbursement updatedReimbursement) {
		return ReimbursementDaoImplementation.getReimbursementDao().updateReimbursement(updatedReimbursement);
	}

	public List<Reimbursement> getAllReimbursements(int status) {
		return ReimbursementDaoImplementation.getReimbursementDao().getAllReimbursements(status);
	}

	public List<Reimbursement> findEmployeesReimbursements(Employee currentEmployee, int status) {
		return ReimbursementDaoImplementation.getReimbursementDao().getAllReimbursementsForEmployee(currentEmployee,
				status);
	}

	public Reimbursement findReimbursement(Reimbursement reimbursementToFind) {
		return ReimbursementDaoImplementation.getReimbursementDao().getReimbursement(reimbursementToFind);
	}

	public List<String> getReimbursementStatus() {
		return ReimbursementDaoImplementation.getReimbursementDao().getReimbursementStatus();
	}

	public List<ReimbursementHistory> getHistory(Reimbursement currentReimbursement) {
		return ReimbursementDaoImplementation.getReimbursementDao().getReimbursementHistory(currentReimbursement);
	}

	public boolean removeReimbursement(Reimbursement currentReimbursement) {
		return ReimbursementDaoImplementation.getReimbursementDao().deleteReimbursement(currentReimbursement);
	}
}
