package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public interface AccountDao {

	public boolean insertAccount(Account insertedAccount);
	public boolean updateAccount(Account updatedAccount);
	public boolean deleteAccount(Account deletedAccount);
	public List<Account> getAllAccounts();
	public List<Account> getAllAccountsForUser(User currentUser);
	public Account getAccount(Account accountToFind);
	public List<String> getAccountTypes();
	public List<Transaction> getAccountTransactions(Account currentAccount);
}
