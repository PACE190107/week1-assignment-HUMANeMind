package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.EmployeeDaoImplementation;
import com.revature.exceptions.CredentialsNotValid;
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

	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		String[] path = request.getRequestURI().split("/");
		String lastURL = path[path.length - 1];
		String nextToLastURL = path[path.length - 2];

		switch (method) {
			case "GET": {
				// GET ALL LOGIC
				if ("employee".equals(lastURL)) { // execute if the request is /PaceServletExamples/rest/todos
					List<Employee> allEmployees = findAllEmployees();
					return allEmployees;
				}
	
				// GET ONE LOGIC
				if ("employee".equals(nextToLastURL)) {
					try {
						Employee employeeId = new Employee();
						employeeId.setId(Integer.valueOf(lastURL));
	
						employeeId = findEmployee(employeeId, "ID");
	
						return employeeId;
					} catch (NumberFormatException e) {
						return "Cannot convert " + lastURL + " into a number";
					} catch (EmployeeNotFoundException e) {
						return e.getMessage();
					}
				}
	
								
				
				if ("employee3".equals(nextToLastURL)) {
					try {
						boolean success = false;
						Employee editEmployee = new Employee(0, lastURL, "1234", "t", "u", "s.u@g.com", 2, "Employee", false);
	
						success = registerEmployee(editEmployee);
	
						if (success) {
							editEmployee = findEmployee(editEmployee, "Name");
							return editEmployee;
						} else {
							throw new CredentialsNotValid();
						}
					} catch (CredentialsNotValid e) {
						return e.getMessage();
					}catch (EmployeeNotFoundException e) {
						return e.getMessage();
					}
				}
			}
			case "POST": {
				if ("login".equals(lastURL)) {
					try {
						System.out.println(request.getParameter("cmd"));
						boolean success = false;
						Employee loginEmployee = new Employee();
						loginEmployee.setUserName("E2");
						loginEmployee.setPassword("1234");
	
						success = validateLogin(loginEmployee.getUserName(), loginEmployee.getPassword());
	
						if (success) {
							loginEmployee = findEmployee(loginEmployee, "Name");
							return loginEmployee;
						} else {
							throw new CredentialsNotValid();
						}
					} catch (CredentialsNotValid e) {
						return e.getMessage();
					}catch (EmployeeNotFoundException e) {
						return e.getMessage();
					}
				}
				if ("newLogin".equals(lastURL)) {
					try {
						boolean success = false;
						Employee newEmployee = new Employee(0, lastURL, "1234", "t", "u", "s.u@g.com", 2, "Employee", false);
	
						success = registerEmployee(newEmployee);
	
						if (success) {
							newEmployee = findEmployee(newEmployee, "Name");
							return newEmployee;
						} else {
							throw new CredentialsNotValid();
						}
					} catch (CredentialsNotValid e) {
						return e.getMessage();
					}catch (EmployeeNotFoundException e) {
						return e.getMessage();
					}
				}				
			}
	
			case "PUT": {
				break;
			}
	
			case "DELETE": {
				//TODO
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
