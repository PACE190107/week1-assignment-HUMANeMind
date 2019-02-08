package com.revature.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.models.Employee;

public interface EmployeeDao {
	public Object process(HttpServletRequest request, HttpServletResponse response);

	public boolean validateEmployeePassword(String userName, String password);
	public boolean insertEmployee(Employee insertedEmployee);
	public boolean updateEmployee(Employee updatedEmployee, String field);
	public boolean deleteEmployee(Employee deletedEmployee);
	public List<Employee> getAllEmployees() ;
	public Employee getEmployee(Employee employeeToFind, String by);
	public String getPermissions(Employee employeeToPermit);
}
