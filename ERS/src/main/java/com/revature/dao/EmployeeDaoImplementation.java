package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.revature.models.Employee;
import com.revature.util.JDBCConnectionUtil;

// Dao class implements DAO interface
// DAO class retrieves data from database
public class EmployeeDaoImplementation implements EmployeeDao {

	private static EmployeeDaoImplementation employeeDao;
	final static Logger log = Logger.getLogger(EmployeeDaoImplementation.class);

	private EmployeeDaoImplementation() {
	}

	public static EmployeeDaoImplementation getEmployeeDao() {
		if (employeeDao == null) {
			employeeDao = new EmployeeDaoImplementation();
		}
		return employeeDao;
	}
	
	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertEmployee(Employee insertedEmployee) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "{CALL INSERT_EMPLOYEE(?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement cs = conn.prepareCall(sql);

			cs.setString(1, null);
			cs.setString(2, insertedEmployee.getUserName());
			cs.setString(3, insertedEmployee.getPassword());
			cs.setString(4, insertedEmployee.getFirstName());
			cs.setString(5, insertedEmployee.getLastName());
			cs.setString(6, insertedEmployee.getEmail());
			cs.setInt(7, insertedEmployee.getPermissionId());
			cs.setInt(8, insertedEmployee.isTemporaryPassword() ? 1: 0);
			if (cs.executeUpdate() > 0) {
				System.out.println("Employee successfully Created.");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("The Username entered already exists.");
			return false;
		}

		return false;
	}
	
	@Override
	public boolean updateEmployee(Employee updatedEmployee, String field) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "UPDATE EMPLOYEE " + 
					"SET " + field + " = ? " + 
					"WHERE EMPLOYEE_ID = ?";
			PreparedStatement ps = conn.prepareCall(sql);
			
			switch (field) {
			case "USER_NAME":
				ps.setString(1, updatedEmployee.getUserName());
				break;
			case "PASSWORD":
				ps.setString(1, updatedEmployee.getPassword());
				break;
			case "FIRST_NAME":
				ps.setString(1, updatedEmployee.getFirstName());
				break;
			case "LAST_NAME":
				ps.setString(1, updatedEmployee.getLastName());
				break;
			case "EMAIL":
				ps.setString(1, updatedEmployee.getEmail());
				break;
			case "PERMISSION_LEVEL":
				ps.setInt(1, updatedEmployee.getPermissionId());
				break;
			}			
			
			ps.setInt(2, updatedEmployee.getEmployeeID());

			if (ps.executeUpdate() > 0) {
				System.out.println("User successfully Modified.");
				return true;
			}

		} catch (SQLException e) {
			System.out.println("The User Name entered is already taken.");
		}

		return false;
	}
	
	@Override
	public boolean deleteEmployee(Employee deletedEmployee) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "{CALL DELETE_EMPLOYEE(?)}";
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setInt(1, deletedEmployee.getEmployeeID());
			
			if (cs.executeUpdate() > 0) {
				System.out.println("Employee successfully Deleted.");
				return true;
			}

		} catch (SQLException e) {
			log.error("Unable to delete Employee due to a system error. Please contact a system administrator.");
		}

		return false;
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Employees
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM EMPLOYEE_WITH_PERMISSION_VW ORDER BY EMPLOYEE_ID DESC";

			ResultSet rs = stmt.executeQuery(sql);

			List<Employee> allEmployees = new ArrayList<Employee>();
			
			// Return all rows in the results
			while (rs.next()) {				
				allEmployees.add(
						 new Employee(rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6),
								rs.getInt(7),
								rs.getString(8),
								rs.getBoolean(9)));
			}
			
			return allEmployees;

		} catch (SQLException e) {
			log.error("Unable to return all users due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	@Override
	public Employee getEmployee(Employee employeeToFind, String by) {		
		PreparedStatement ps;
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Query to find a specific User
			if (by == "Name") {
				String sql = "SELECT * FROM EMPLOYEE_WITH_PERMISSION_VW WHERE USER_NAME = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, employeeToFind.getUserName());
			} else {
				String sql = "SELECT * FROM EMPLOYEE_WITH_PERMISSION_VW WHERE EMPLOYEE_ID = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, employeeToFind.getEmployeeID());
			}

			ResultSet rs = ps.executeQuery();
			
			if (rs.getFetchSize() > 0) {
				// Store the results
				while (rs.next()) {
					employeeToFind = new Employee(rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getInt(7),
							rs.getString(8),
							rs.getBoolean(9));
				}
				return employeeToFind;
			}

		} catch (SQLException e) {
			log.error("Unable to find this Employee due to a system error. Please contact a system administrator.");
		}
		return null;
	}

	@Override
	public boolean validateEmployeePassword(String employeeName, String password) {
		int isValid = 0;
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Run the Stored Procedure to see if the User entered a valid password for their account
			String sql = "{CALL VALIDATE_PASSWORD(?, ?, ?)}";
			CallableStatement cs = conn.prepareCall(sql);
			cs.setString(1, employeeName);
			cs.setString(2, password);
			cs.registerOutParameter(3, Types.INTEGER);			
			cs.execute();
			
			isValid = cs.getInt(3);

			if (isValid > 0) {
				return true;
			}

		} catch (SQLException e) {
			log.error("Unable to validate password due to a system error. Please contact a system administrator.");
		}
		return false;
	}
	
	@Override
	public String getPermissions(Employee employeeToPermit) {
		String permissions = "";
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Query to find a specific User
			String sql = "SELECT PERMISSION_NAME FROM EMPLOYEE_WITH_PERMISSION_VW WHERE EMPLOYEE_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeToPermit.getEmployeeID());

			ResultSet rs = ps.executeQuery();

			if (rs.getFetchSize() > 0) {
				
				// Store the results
				while (rs.next()) {
					permissions += rs.getString(1) + " ";
				}
				
				permissions.trim();
				return permissions;
			}

		} catch (SQLException e) {
			log.error("Unable to find permissions for this Employee due to a system error. Please contact a system administrator.");
		}

		return null;
	}
}