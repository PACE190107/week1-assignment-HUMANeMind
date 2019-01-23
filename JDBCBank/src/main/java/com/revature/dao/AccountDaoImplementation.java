package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.util.JDBCConnectionUtil;

// Dao class implements DAO interface
// DAO class retrieves data from database
public class AccountDaoImplementation implements AccountDao {
	private static AccountDaoImplementation accountDao;
	final static Logger log = Logger.getLogger(AccountDaoImplementation.class);

	private AccountDaoImplementation() {
	}

	public static AccountDaoImplementation getAccountDao() {
		if (accountDao == null) {
			accountDao = new AccountDaoImplementation();
		}
		return accountDao;
	}
	
	@Override
	public boolean insertAccount(Account insertedAccount) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "{CALL INSERT_ACCOUNTS(?, ?, ?, ?, ?, ?)}";
			CallableStatement cs = conn.prepareCall(sql);

			cs.setString(1, null);
			cs.setString(2, insertedAccount.getNickName());
			cs.setDouble(3, insertedAccount.getBalance());
			cs.setInt(4, insertedAccount.getType());
			cs.setInt(5, insertedAccount.getUpdatedBy());
			cs.setInt(6, insertedAccount.getUserID());
			
			if (cs.executeUpdate() > 0) {
				System.out.println("Account Created successfully.");
				return true;
			}

		} catch (SQLException e) {
			log.error("Unable to create account due to a system error. Please contact a system administrator.");
		}

		return false;
	}

	@Override
	public boolean updateAccount(Account updatedAccount) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "UPDATE ACCOUNTS " + 
					"SET BALANCE = ?, " + 					 
					"UPDATED_BY = ? " +
					"WHERE ACCOUNT_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setDouble(1, updatedAccount.getBalance());			
			ps.setInt(2, updatedAccount.getUpdatedBy());
			ps.setInt(3, updatedAccount.getId());
			
			if (ps.executeUpdate() > 0) {
				System.out.println("Account Balance successfully altered.");
				return true;
			}

		} catch (SQLException e) {			
			log.error("Unable to update account due to a system error. Please contact a system administrator.");
		}

		return false;
	}

	@Override
	public boolean deleteAccount(Account deletedAccount) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "{CALL DELETE_ACCOUNT(?)}";
			CallableStatement cs = conn.prepareCall(sql);
			
			cs.setInt(1, deletedAccount.getId());
			
			if (cs.executeUpdate() > 0) {
				System.out.println("Account Deleted successfully");
				return true;
			}

		} catch (SQLException e) {
			log.error("Unable to delete account due to a system error. Please contact a system administrator.");
		}

		return false;
	}

	@Override
	public List<Account> getAllAccounts() {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Accounts
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM ACCOUNTS_WITH_TYPES_VW";

			ResultSet rs = stmt.executeQuery(sql);

			List<Account> allAccounts = new ArrayList<Account>();
			
			// Return all rows in the results
			while (rs.next()) {				
				allAccounts.add(
						new Account(rs.getInt(1),
								rs.getString(2),
								rs.getInt(3),
								rs.getInt(4),
								rs.getString(5),
								rs.getInt(6),
								rs.getInt(7)));
			}

			return allAccounts;

		} catch (SQLException e) {
			log.error("Unable to return all accounts due to a system error. Please contact a system administrator.");
		}

		return null;
	}
	
	@Override
	public List<Account> getAllAccountsForUser(User currentUser) {
		List<Account> allAccounts = new ArrayList<Account>();
		
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Accounts for this user
			String sql = "SELECT * FROM ACCOUNTS_WITH_TYPES_VW WHERE USER_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);			
			ps.setInt(1, currentUser.getId());
			
			ResultSet rs = ps.executeQuery();			
			
			// Return all rows in the results
			while (rs.next()) {				
				allAccounts.add(
						new Account(rs.getInt(1),
								rs.getString(2),
								rs.getInt(3),
								rs.getInt(4),
								rs.getString(5),
								rs.getInt(6),
								rs.getInt(7)));
			}

			return allAccounts;

		} catch (SQLException e) {
			System.out.println("Unable to return all of your accounts due to a system error. Please contact a system administrator.");
		}

		return null;
	}	

	@Override
	public Account getAccount(Account accountToFind) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Query to find a specific User
			String sql = "SELECT * FROM ACCOUNTS_WITH_TYPES_VW WHERE ACCOUNT_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, accountToFind.getId());
			
			ResultSet rs = ps.executeQuery();
			if (rs.getFetchSize() > 0) {
				// Store the results
				while (rs.next()) {
					accountToFind = new Account(rs.getInt(1),
							rs.getString(2),
							rs.getInt(3),
							rs.getInt(4),
							rs.getString(5),
							rs.getInt(6),
							rs.getInt(7));
				}
				return accountToFind;
			}

		} catch (SQLException e) {
			log.error("Unable to return this accounts due to a system error. Please contact a system administrator.");
		}

		return null;
	}
	
	@Override
	public List<String> getAccountTypes() {
		List<String> types = new ArrayList<String>();
		
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Account Types
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM TYPES";

			ResultSet rs = stmt.executeQuery(sql);
			
			// Return all rows in the results
			while (rs.next()) {
				types.add(rs.getInt(1) + ";" + rs.getString(2));
			}

			return types;

		} catch (SQLException e) {
			log.error("Unable to return all account types due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	@Override
	public List<Transaction> getAccountTransactions(Account currentAccount) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Account Types			
			String sql = "SELECT * FROM TRANSACTION_LOG WHERE FROM_ACCOUNT = ? ORDER BY EVENT_TIME DESC";
			PreparedStatement ps = conn.prepareStatement(sql);			
			ps.setInt(1, currentAccount.getId());
			
			ResultSet rs = ps.executeQuery();			
			
			// Return all rows in the results
			while (rs.next()) {
				
				transactions.add(
						new Transaction(rs.getInt(1),
								rs.getInt(2), 
								rs.getInt(3),
								rs.getInt(4),
								rs.getInt(5),
								rs.getTimestamp(6).toLocalDateTime()));
			}
			return transactions;

		} catch (SQLException e) {
			log.error("Unable to return all account transactions due to a system error. Please contact a system administrator.");
		}

		return null;
	}	
}