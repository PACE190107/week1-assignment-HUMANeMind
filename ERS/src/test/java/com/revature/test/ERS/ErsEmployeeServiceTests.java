package com.revature.test.ERS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.models.Employee;
import com.revature.services.EmployeeServiceImplementation;

public class ErsEmployeeServiceTests {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/*******************************************************************
	 * Test User
	 ******************************************************************/
	@Test
	public void validationFailed() {
		String employeeName = "E1";
		String password = "1";

		assertFalse(EmployeeServiceImplementation.getEmployeeService().validateLogin(employeeName, password));
	}

	@Test
	public void validationSucceed() {
		String employeeName = "E1";
		String password = "1234";

		assertTrue(EmployeeServiceImplementation.getEmployeeService().validateLogin(employeeName, password));
	}

	@Test
	public void findAllEmployees() {
		List<Employee> allEmployees = new ArrayList<Employee>();
		allEmployees.add(new Employee(101, "E1", "DD4154387E3E76B95931889591F77131", "Regular", "Employee",
				"regular.employee@gmail.com", 1, "Regular", false));
		allEmployees.add(new Employee(102, "E2", "5DEE0285D86F6975DF94BDB9B4543869", "Manager", "Employee",
				"manager.employee@gmail.com", 2, "Manager", false));
		allEmployees.add(new Employee(103, "E3", "5811961DB663FD20DF106CCE1651BE64", "Regular", "Person",
				"regular.person@gmail.com", 1, "Regular", false));
		
		assertEquals(allEmployees, EmployeeServiceImplementation.getEmployeeService().findAllEmployees());
	}

	@Test
	public void findEmployeeByName() {
		Employee newEmployee = new Employee(101, "E1", "DD4154387E3E76B95931889591F77131", "Regular", "Employee",
				"regular.employee@gmail.com", 1, "Regular", false);
		Employee foundEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "Name");

		assertEquals(foundEmployee, newEmployee);
	}

	@Test
	public void findEmployeeByID() {
		Employee newEmployee = new Employee(101, "E1", "DD4154387E3E76B95931889591F77131", "Regular", "Employee",
				"regular.employee@gmail.com", 1, "Regular", false);
		Employee foundEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "ID");

		assertEquals(foundEmployee, newEmployee);
	}
	@Test
	public void createExistingEmployee() {
		Employee newEmployee = new Employee();
		newEmployee = new Employee(0, "E1", "1234", "t", "u", "s.u@g.com", 1, "Manager", false);

		assertFalse(EmployeeServiceImplementation.getEmployeeService().registerEmployee(newEmployee));
	}

	@Test
	public void createNewEmployee() {
		Employee newEmployee = new Employee();
		newEmployee = new Employee(0, "T1", "1234", "t", "u", "s.u@g.com", 2, "Employee", false);

		assertTrue(EmployeeServiceImplementation.getEmployeeService().registerEmployee(newEmployee));
	}

	@Test
	public void deleteEmployee() {
		Employee newEmployee = new Employee();
		newEmployee = new Employee();
		newEmployee.setUserName("T1");
		newEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "Name");

		assertTrue(EmployeeServiceImplementation.getEmployeeService().removeEmployee(newEmployee));
	}
	
	@Test
	public void findPermission() {
		String permissions;
		Employee newEmployee = new Employee();
		newEmployee.setUserName("E2");
		Employee foundEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "Name");
		permissions = EmployeeServiceImplementation.getEmployeeService().findPermissions(foundEmployee);

		assertEquals("Manager ", permissions);
	}

	@Test
	public void updateEmployeeFirstName() {
		Employee newEmployee = new Employee();
		newEmployee.setUserName("E3");
		Employee foundEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "Name");

		foundEmployee.setFirstName("test");
		assertTrue(EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "FIRST_NAME"));
		foundEmployee.setFirstName("Regular");
		EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "FIRST_NAME");
	}

	@Test
	public void updateEmployeeLastName() {
		Employee newEmployee = new Employee();
		newEmployee.setUserName("E3");
		Employee foundEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "Name");

		foundEmployee.setLastName("test");
		assertTrue(EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "LAST_NAME"));
		foundEmployee.setLastName("Person");
		EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "LAST_NAME");
	}

	@Test
	public void updateEmployeeEmail() {
		Employee newEmployee = new Employee();
		newEmployee.setUserName("E3");
		Employee foundEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "Name");

		foundEmployee.setEmail("test");
		assertTrue(EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "EMAIL"));
		foundEmployee.setEmail("regular.person@gmail.com");
		EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "EMAIL");
	}
	
	@Test
	public void updateEmployeePermissions() {
		Employee newEmployee = new Employee();
		newEmployee.setUserName("E3");
		Employee foundEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "Name");

		foundEmployee.setPermissionId(2);
		assertTrue(EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "PERMISSION_LEVEL"));
		foundEmployee.setPermissionId(1);
		EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "PERMISSION_LEVEL");
	}
	
	@Test
	public void updateEmployeeUserNameandPassword() {
		Employee newEmployee = new Employee();
		newEmployee.setId(103);
		Employee foundEmployee = EmployeeServiceImplementation.getEmployeeService().findEmployee(newEmployee, "ID");

		foundEmployee.setUserName("E4");
		foundEmployee.setPassword("4321");
		assertTrue(EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "USER_NAME"));
		assertTrue(EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "PASSWORD"));
		foundEmployee.setUserName("E3");
		foundEmployee.setPassword("1234");
		EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "USER_NAME");
		EmployeeServiceImplementation.getEmployeeService().modifyEmployee(foundEmployee, "PASSWORD");
	}
}