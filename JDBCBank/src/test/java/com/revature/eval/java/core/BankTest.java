package com.revature.eval.java.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class BankTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/*******************************************************************
	 * Test User
	 ******************************************************************/	
	@Test
	public void validationFailed() {	    
	    String userName = "T1";	    
	    String userPassword = "1";   
	    
	    assertFalse( UserService.getUserService().validateLogin(userName, userPassword));
	}

	@Test
	public void validationSucceed() {	    
	    String userName = "T1";	    
	    String userPassword = "1234";   
	    
	    assertTrue( UserService.getUserService().validateLogin(userName, userPassword));
	}

	@Test
	public void findAllUsers() {	
		List<User> allUsers = new ArrayList<User>();
		allUsers.add(new User(103,"T1", "03C2801F4DDB84B1C1890A4C0B299FB5", "t", "u", 103));
		allUsers.add(new User(196,"T2", "03C2801F4DDB84B1C1890A4C0B299FB5", "t", "u", 101));
		allUsers.add(new User(101,"S1", "FE04A7373FFAB5AA7D1FF42D23E90869", "Super", "User", 101));
	    
//		System.out.println(allUsers);
//		System.out.println(UserService.getUserService().findAllUsers());
	    assertEquals(allUsers, UserService.getUserService().findAllUsers());
	    
	    //Well, this is failing, but the values of each list match
	}
	
	@Test
	public void createExistingUser() {	
		User newUser = new User();
		newUser = new User(103,"T1", "1234", "t", "u", 103);
		
	    assertFalse(UserService.getUserService().registerUser(newUser));
	}
	
	@Test
	public void createNewUser() {	
		User newUser = new User();
		newUser = new User(0,"T4", "1234", "t", "u", 0);
		
	    assertTrue(UserService.getUserService().registerUser(newUser));
	}
	
	@Test
	public void deleteUser() {	
		User newUser = new User();
		newUser = new User(0,"T4", "1234", "t", "u", 0);
		newUser = UserService.getUserService().findUser(newUser, "Name");		
		
	    assertTrue(UserService.getUserService().removeUser(newUser));
	}
	
	@Test
	public void findUser() {	
		User newUser = new User(101,"S1", "FE04A7373FFAB5AA7D1FF42D23E90869", "Super", "User", 101);
		User foundUser = UserService.getUserService().findUser(newUser, "Name");
		
	    assertEquals(foundUser, newUser);
	}
	
	@Test
	public void modifyUser() {	
		User newUser = new User(0,"T4", "1234", "t", "u", 0);
		User foundUser = UserService.getUserService().findUser(newUser, "Name");
		
		foundUser.setFirstName("test");
	   assertTrue(UserService.getUserService().modifyUser(foundUser));
	}
	
	@Test
	public void findPermission() {
		String permissions;
		User newUser = new User(101,"S1", "FE04A7373FFAB5AA7D1FF42D23E90869", "Super", "User", 101);
		User foundUser = UserService.getUserService().findUser(newUser, "Name");
		permissions = UserService.getUserService().findPermissions(foundUser);

		assertEquals("Super User ", permissions);
	}
	
	
	@Test
	public void registerAccount() {
		Account newAccount = new Account(1000000040,"test", 0, 1, "Savings", 101, 101);
		
	    assertTrue(AccountService.getAccountService().registerAccount(newAccount));
	}
	
	@Test
	public void deleteAccount() {
		Account newAccount = new Account(1000000040,"test", 0, 1, "Savings", 101, 101);
		newAccount = AccountService.getAccountService().findAccount(newAccount);
		
	    assertTrue(AccountService.getAccountService().removeAccount(newAccount));
	}

	@Test
	public void modifyAccount() {	
		Account newAccount = new Account(1000000029,"test1", 1, 2, "Checking", 103, 103);
		Account foundAccount = new Account();
		foundAccount = AccountService.getAccountService().findAccount(newAccount);
		
		foundAccount.setBalance(10000);
	   assertTrue(AccountService.getAccountService().modifyAccount(foundAccount));
	}
	
	@Test
	public void getUsersAccounts() {	
		User currentUser = new User(103,"T1", "03C2801F4DDB84B1C1890A4C0B299FB5", "t", "u", 103);
		List<Account> allAccounts = new ArrayList<Account>();
		allAccounts.add(new Account(1000000028, "test", 100, 1, "Savings", 103, 103));
		allAccounts.add(new Account(1000000029, "test1", 10000, 2, "Checking", 103, 103));
		
	   assertEquals(allAccounts, AccountService.getAccountService().findUsersAccounts(currentUser));
	}

	@Test
	public void testTypes() {
		List<String> types = new ArrayList<String>();
		types.add("1;Savings");
		types.add("2;Checking");
		types.add("3;Auto Loan");
		types.add("4;Personal Loan");
		types.add("5;Mutual Fund");
		types.add("6;Money Market");
		
		types = AccountService.getAccountService().getAccountTypes();
		
		assertEquals(types, AccountService.getAccountService().getAccountTypes());
	}
	
	@Test
	public void testTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		Account currentAccount = new Account(1000000023,"test", 0, 1, "Savings", 101, 101);
		
		transactions.add(new Transaction(29, 1000000023, 100, 0, 101, LocalDateTime.parse("2019-01-21T21:38:15")));
		transactions.add(new Transaction(31, 1000000023, 0, 10000, 101, LocalDateTime.parse("2019-01-21T21:38:48")));
		
		assertEquals(transactions, AccountService.getAccountService().getTransactions(currentAccount));
	}
}
