package com.revature.services;

import java.util.List;

import com.revature.dao.AccountDaoImplementation;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public class AccountService {
	
	private static AccountService accountService;
	
	private AccountService() {
		
	}
	
	public static AccountService getAccountService() {
		if(accountService == null) {
			accountService = new AccountService();
		}
		return accountService;		
	}	
	
	public boolean registerAccount(Account insertedAccount) {
		return AccountDaoImplementation.getAccountDao().insertAccount(insertedAccount);
	}
	
	public boolean modifyAccount(Account updatedAccount) {
		return AccountDaoImplementation.getAccountDao().updateAccount(updatedAccount);
	}
	
	public boolean removeAccount(Account deletedAccount) {
		return AccountDaoImplementation.getAccountDao().deleteAccount(deletedAccount);
	}
	
	public List<Account> getAllAccounts() {
		return AccountDaoImplementation.getAccountDao().getAllAccounts();		
	}
	
	public List<Account> findUsersAccounts(User currentUser) {
		return AccountDaoImplementation.getAccountDao().getAllAccountsForUser(currentUser);		
	}
	
	public Account findAccount(Account accountToFind) {
		return AccountDaoImplementation.getAccountDao().getAccount(accountToFind);
	}
	
	public List<String> getAccountTypes(){
		return AccountDaoImplementation.getAccountDao().getAccountTypes();		
	}
	
	public List<Transaction> getTransactions(Account currentAccount){
		return AccountDaoImplementation.getAccountDao().getAccountTransactions(currentAccount);	
	}
}
