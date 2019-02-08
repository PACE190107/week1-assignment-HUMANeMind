package com.revature.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.ReimbursementDaoImplementation;
import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.exceptions.ReimbursementNotCreatedException;
import com.revature.exceptions.ReimbursementNotFoundException;
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

	@SuppressWarnings("null")
	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		String path = request.getRequestURI();
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		BufferedReader br;
		Employee fromWeb = null;
		Employee managerWeb = null;
		Reimbursement reimbursementWeb = null;
		boolean success = false;

		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			if (br != null) {
				json = br.readLine();
			}
			
			if (json.contains("employeeID")) {
				System.out.println(json);
				System.out.println(path);
				fromWeb = mapper.readValue(json, Employee.class);
			} else {
				System.out.println(json);
				System.out.println(path);
				reimbursementWeb = mapper.readValue(json, Reimbursement.class);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		;

		switch (method) {
		case "GET": {
			// GET ALL LOGIC
			if (path.contains("reimbursementsAll")) {
				List<Reimbursement> allReimbursements = getAllReimbursements(1);
				return allReimbursements;
			}
		}
		case "POST": {
			// GET ONE LOGIC
			if (path.contains("reimbursementID")) {
				try {
					reimbursementWeb = findReimbursement(reimbursementWeb, "ID");
					return reimbursementWeb;
				} catch (NumberFormatException e) {
					return "Cannot convert " + reimbursementWeb.getId() + " into a number";
				} catch (EmployeeNotFoundException e) {
					return e.getMessage();
				}
			}

			if (path.contains("reimbursementCreate")) {
				System.out.println(reimbursementWeb);
				try {
					success = registerReimbursement(reimbursementWeb);

					if (success) {
						reimbursementWeb = findReimbursement(reimbursementWeb, "Submitter");
						return reimbursementWeb.getId();
					} else {
						throw new ReimbursementNotCreatedException();
					}
				} catch (ReimbursementNotCreatedException e) {
					return e.getMessage();
				} catch (ReimbursementNotFoundException e) {
					return e.getMessage();
				}
			}

			if (path.contains("reimbursementByEmployee")) {
				try {
					List<Reimbursement> employeesReimbursements = findEmployeesReimbursements(fromWeb);
					return employeesReimbursements;
				} catch (NumberFormatException e) {
					return "Cannot convert " + fromWeb.getEmployeeID() + " into a number";
				} catch (ReimbursementNotFoundException e) {
					return e.getMessage();
				}
			}
		}

		case "PUT": {
			if (path.contains("reibursementUpdate")) {
				try {
					if (managerWeb.getPermissionId() == 2) {
						success = modifyReimbursement(reimbursementWeb);
						if (!success) {
							return "Status Update Failed";
						}
					}
				} catch (ReimbursementNotFoundException e) {
					return e.getMessage();
				}
				break;
			}
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

	public List<Reimbursement> findEmployeesReimbursements(Employee currentEmployee) {
		return ReimbursementDaoImplementation.getReimbursementDao().getAllReimbursementsForEmployee(currentEmployee);
	}

	public Reimbursement findReimbursement(Reimbursement reimbursementToFind, String by) {
		return ReimbursementDaoImplementation.getReimbursementDao().getReimbursement(reimbursementToFind, by);
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
