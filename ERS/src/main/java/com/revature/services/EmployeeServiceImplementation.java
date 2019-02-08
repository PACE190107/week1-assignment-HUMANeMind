package com.revature.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDaoImplementation;
import com.revature.exceptions.CredentialsNotValid;
import com.revature.exceptions.EmployeeNotCreatedException;
import com.revature.exceptions.EmployeeNotFoundException;
import com.revature.models.Employee;

public class EmployeeServiceImplementation implements EmployeeService {

	private static EmployeeServiceImplementation employeeServiceImpl;

	private EmployeeServiceImplementation() {

	}

	public static EmployeeServiceImplementation getEmployeeService() {
		if (employeeServiceImpl == null) {
			employeeServiceImpl = new EmployeeServiceImplementation();
		}
		return employeeServiceImpl;
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
		boolean success = false;

		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			if (br != null) {
				json = br.readLine();
			}
			fromWeb = mapper.readValue(json, Employee.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		};

		switch (method) {
		case "GET": {
			if (managerWeb.getPermissionId() == 2) {
				if (path.contains("employeesAll")) {
					List<Employee> allEmployees = findAllEmployees();
					return allEmployees;
				}
			}			
		}

		case "POST": {
			if (path.contains("employeeLogin")) {
				try {
					success = validateLogin(fromWeb.getUserName(), fromWeb.getPassword());

					if (success) {
						fromWeb = findEmployee(fromWeb, "Name");
						return fromWeb;
					} else {
						throw new CredentialsNotValid();
					}
				} catch (CredentialsNotValid e) {
					return e.getMessage();
				} catch (EmployeeNotFoundException e) {
					return e.getMessage();
				}
			}

			if (path.contains("employeeNew")) {
				try {
					success = registerEmployee(fromWeb);

					if (success) {
						fromWeb = findEmployee(fromWeb, "Name");
						return fromWeb;
					} else {
						throw new EmployeeNotCreatedException(fromWeb.getUserName());
					}
				} catch (EmployeeNotCreatedException e) {
					return e.getMessage();
				} catch (EmployeeNotFoundException e) {
					return e.getMessage();
				}
			}

			if (path.contains("employeeFind")) {
				try {
					fromWeb = findEmployee(fromWeb, "ID");
					return fromWeb;
				} catch (NumberFormatException e) {
					return "Cannot convert " + fromWeb.getEmployeeID() + " into a number";
				} catch (EmployeeNotFoundException e) {
					return e.getMessage();
				}
			}
		}

		case "PUT": {
			if (path.contains("employeeUpdate")) {
				try {
					Employee previousInfo = findEmployee(fromWeb, "ID");

					if (previousInfo.getPassword() != fromWeb.getPassword()) {
						success = modifyEmployee(fromWeb, "PASSWORD");
						if (!success) {
							return "Password Update Failed";
						}
					}

					if (previousInfo.getEmail() != fromWeb.getEmail()) {
						success = modifyEmployee(fromWeb, "EMAIL");
						if (!success) {
							return "Email Update Failed";
						}
					}

					if (previousInfo.getFirstName() != fromWeb.getFirstName()) {
						success = modifyEmployee(fromWeb, "FIRST_NAME");
						if (!success) {
							return "First Name Update Failed";
						}
					}

					if (previousInfo.getEmail() != fromWeb.getEmail()) {
						success = modifyEmployee(fromWeb, "LAST_NAME");
						if (!success) {
							return "Last Name Update Failed";
						}
					}

					if (previousInfo.getPermissionId() != fromWeb.getPermissionId()) {
						success = modifyEmployee(fromWeb, "PERMISSION_LEVEL");
						if (!success) {
							return "Last Name Update Failed";
						}
					}

					if (success) {
						return "Success!";
					} else {
						throw new CredentialsNotValid();
					}
				} catch (EmployeeNotFoundException e) {
					return e.getMessage();
				}
			}
			break;
		}

		case "DELETE": {
			// TODO
			break;
		}
		}

		return null;
	}

	public List<Employee> findAllEmployees() {
		return EmployeeDaoImplementation.getEmployeeDao().getAllEmployees();
	}

	public boolean registerEmployee(Employee insertedEmployee) {
		return EmployeeDaoImplementation.getEmployeeDao().insertEmployee(insertedEmployee);
	}

	public boolean modifyEmployee(Employee updatedEmployee, String field) {
		return EmployeeDaoImplementation.getEmployeeDao().updateEmployee(updatedEmployee, field);
	}

	public boolean removeEmployee(Employee deletedEmployee) {
		return EmployeeDaoImplementation.getEmployeeDao().deleteEmployee(deletedEmployee);
	}

	public Employee findEmployee(Employee employeeToFind, String by) {
		return EmployeeDaoImplementation.getEmployeeDao().getEmployee(employeeToFind, by);
	}

	public boolean validateLogin(String employeeName, String password) {
		return EmployeeDaoImplementation.getEmployeeDao().validateEmployeePassword(employeeName, password);
	}

	public String findPermissions(Employee employeeToPermit) {
		return EmployeeDaoImplementation.getEmployeeDao().getPermissions(employeeToPermit);
	}
}
