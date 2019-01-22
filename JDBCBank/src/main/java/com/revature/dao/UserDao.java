package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDao {
	public boolean validateUserPassword(String userName, String userPassword);
	public boolean insertUser(User insertedUser);
	public boolean updateUser(User updatedUser);
	public boolean deleteUser(User deletedUser);
	public List<User> getAllUsers() ;
	public User getUser(User userToFind, String by);
	public String getPermissions(User userToPermit);	
}
