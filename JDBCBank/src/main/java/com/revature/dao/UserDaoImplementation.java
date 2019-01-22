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
import org.apache.log4j.Logger;
import com.revature.models.User;
import com.revature.util.JDBCConnectionUtil;

// Dao class implements DAO interface
// DAO class retrieves data from database
public class UserDaoImplementation implements UserDao {

	private static UserDaoImplementation userDao;
	final static Logger log = Logger.getLogger(UserDaoImplementation.class);

	private UserDaoImplementation() {
	}

	public static UserDaoImplementation getUserDao() {
		if (userDao == null) {
			userDao = new UserDaoImplementation();
		}
		return userDao;
	}

	@Override
	public boolean insertUser(User insertedUser) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "{CALL INSERT_USER(?, ?, ?, ?, ?, ?)}";
			CallableStatement cs = conn.prepareCall(sql);

			cs.setString(1, null);
			cs.setString(2, insertedUser.getFirstName());
			cs.setString(3, insertedUser.getLastName());
			cs.setString(4, insertedUser.getUserName());
			cs.setString(5, insertedUser.getPassword());
			cs.setInt(6, insertedUser.getUpdatedBy());
			
			if (cs.executeUpdate() > 0) {
				System.out.println("User successfully Created.");
				return true;
			}

		} catch (SQLException e) {
			System.out.println("The User Name entered already exists.");
			return false;
		}

		return false;
	}
	
	@Override
	public boolean updateUser(User updatedUser) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "UPDATE USERS " + 
					"SET USER_NAME = ?, " + 
					"PASSWORD = ?, " + 
					"FIRST_NAME = ?, " + 
					"LAST_NAME = ?, " + 
					"UPDATED_BY = ? " + 
					"WHERE USER_ID = ?";
			PreparedStatement ps = conn.prepareCall(sql);

			ps.setString(1, updatedUser.getUserName());
			ps.setString(2, updatedUser.getPassword());
			ps.setString(3, updatedUser.getFirstName());
			ps.setString(4, updatedUser.getLastName());			
			ps.setInt(5, updatedUser.getUpdatedBy());
			ps.setInt(6, updatedUser.getId());
			
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
	public boolean deleteUser(User deletedUser) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "{CALL DELETE_USER(?)}";
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setInt(1, deletedUser.getId());
			
			if (cs.executeUpdate() > 0) {
				System.out.println("User successfully Deleted.");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Unable to delete user due to a system error. Please contact a system administrator.");
		}

		return false;
	}

	@Override
	public List<User> getAllUsers() {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Users
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM USERS";

			ResultSet rs = stmt.executeQuery(sql);

			List<User> allUsers = new ArrayList<User>();
			
			// Return all rows in the results
			while (rs.next()) {				
				allUsers.add(
						new User(rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getInt(6)));
			}

			return allUsers;

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Unable to return all users due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	@Override
	public User getUser(User userToFind, String by) {		
		PreparedStatement ps;
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Query to find a specific User
			if (by == "Name") {
				String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userToFind.getUserName());
			} else {
				String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, userToFind.getId());
			}

			ResultSet rs = ps.executeQuery();

			if (rs.getFetchSize() > 0) {

				// Store the results
				while (rs.next()) {
					userToFind = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getInt(6));
				}
				return userToFind;
			}

		} catch (SQLException e) {
			log.error("Unable to find this user due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	@Override
	public boolean validateUserPassword(String userName, String userPassword) {
		int isValid = 0;
		try (Connection conn = JDBCConnectionUtil.getConnection()) {			
			// Run the Stored Procedure to see if the User entered a valid password for their account
			String sql = "{CALL VALIDATE_PASSWORD(?, ?, ?)}";
			CallableStatement cs = conn.prepareCall(sql);
			cs.setString(1, userName);
			cs.setString(2, userPassword);
			cs.registerOutParameter(3, Types.INTEGER);			
			cs.execute();
			
			isValid = cs.getInt(3);

			if (isValid > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Unable to validate password due to a system error. Please contact a system administrator.");
		}
		return false;
	}

	@Override
	public String getPermissions(User userToPermit) {
		String permissions = "";
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Query to find a specific User
			String sql = "SELECT ACCESS_NAME FROM USERS_WITH_ACCESS_VW WHERE USER_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userToPermit.getId());

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
			e.printStackTrace();
			log.error("Unable to find permissions for this user due to a system error. Please contact a system administrator.");
		}

		return null;
	}
	
}