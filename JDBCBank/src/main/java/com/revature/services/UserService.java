package com.revature.services;

import java.util.List;

import com.revature.dao.UserDaoImplementation;
import com.revature.models.User;

public class UserService {
	
	private static UserService userService;
	
	private UserService() {
		
	}
	
	public static UserService getUserService() {
		if(userService == null) {
			userService = new UserService();
		}
		return userService;		
	}
	
	public List<User> findAllUsers() {
		return UserDaoImplementation.getUserDao().getAllUsers();		
	}
	
	public boolean registerUser(User insertedUser) {
		return UserDaoImplementation.getUserDao().insertUser(insertedUser);
	}
	
	public boolean modifyUser(User updatedUser) {
		return UserDaoImplementation.getUserDao().updateUser(updatedUser);
	}
	
	public boolean removeUser(User deletedUser) {
		return UserDaoImplementation.getUserDao().deleteUser(deletedUser);
	}
	
	public User findUser(User userToFind, String by) {
		return UserDaoImplementation.getUserDao().getUser(userToFind, by);
	}	
	
	public boolean validateLogin(String userName, String userPassword) {
		return UserDaoImplementation.getUserDao().validateUserPassword(userName, userPassword);
	}

	public String findPermissions(User userToPermit) {
		return UserDaoImplementation.getUserDao().getPermissions(userToPermit);
	}
}
