package com.revature;

import java.util.*;

import org.apache.log4j.Logger;

import com.revature.dao.AccountDaoImplementation;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class BankApplication {
	final static Logger log = Logger.getLogger(AccountDaoImplementation.class);
	static Scanner sc = new Scanner(System.in);
	static User userDetails = new User();
	static List<User> usersList;
	static List<Account> accountsList;
	static String permissions;
	static String validSelection;
	static boolean isSuper = false;
	static boolean isValid = false;
	static BankApplication bankApplication = new BankApplication();

	public static void main(String[] args) {
		boolean loginSuccessful = false;

		loginSuccessful = bankApplication.createOrLogin();

		if (loginSuccessful) {
			userDetails = UserService.getUserService().findUser(userDetails, "Name");
			System.out.println("Welcome " + userDetails.getFirstName() + ".");
			permissions = UserService.getUserService().findPermissions(userDetails);

			bankApplication.bankMain();
		}
	}

	public void bankMain() {
		validSelection = "PAL";
		isValid = false;
		char entered = ' ';
		// Manager and Teller permissions are not implemented at this time
		// So all users at this point will be either a User or a Super
		if (permissions.contains("Super")) {
			isSuper = true;
			validSelection += "U";
		}

		while (!isValid) {
			entered = bankApplication.displayOptions();

			for (char e : validSelection.toCharArray()) {
				if (e == entered) {
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				System.out.println("Entry wasn't valid.");
			}
		}

		switch (entered) {
		case 'U':
			if (isSuper) {
				System.out.println("Manage Users:");
				bankApplication.manageUsers();
			} else {
				System.out.println("You shouldn't be able to see this message. Please contact a system administrator.");
			}
			break;
		case 'P':
			System.out.println("Manage Your Profile:");
			bankApplication.manageProfile();
			break;
		case 'A':
			System.out.println("Manage Accounts:");
			bankApplication.manageAccounts();
			break;
		case 'L':
			bankApplication.logout();
			break;
		}
	}

	public boolean createOrLogin() {
		String typeOfLogin;
		boolean loginCondition = false;

		System.out.println("1 to Login");
		System.out.println("2 to Create a New User");
		System.out.println("anything else to Exit");
		System.out.println("Enter Selection: ");

		typeOfLogin = sc.nextLine();

		switch (typeOfLogin) {
		case "1":
			while (!loginCondition) {
				userDetails = inputLogin();

				loginCondition = UserService.getUserService().validateLogin(userDetails.getUserName(),
						userDetails.getPassword());

				if (loginCondition) {
					return loginCondition;
				} else {
					System.out.println("The username and password combination entered doesn't match our system.");
				}
			}

		case "2":
			while (!loginCondition) {
				userDetails = newLogin();
				loginCondition = UserService.getUserService().registerUser(userDetails);
			}
			return loginCondition;
		default:
			logout();
			return false;
		}
	}

	public User inputNames() {
		String firstName;
		String lastName;
		User tempUser = new User();

		System.out.println("First Name: ");
		firstName = sc.nextLine();
		System.out.println("Last Name: ");
		lastName = sc.nextLine();

		tempUser.setFirstName(firstName);
		tempUser.setLastName(lastName);

		return tempUser;
	}

	public User inputLogin() {
		String userName = null;
		String password = null;
		User tempUser = new User();

		System.out.println("User Name: ");
		userName = sc.nextLine();
		System.out.println("Password: ");
		password = sc.nextLine();

		tempUser.setUserName(userName);
		tempUser.setPassword(password);

		return tempUser;
	}

	public User newLogin() {
		User tempUser = new User();
		User tempUser2 = new User();

		tempUser = bankApplication.inputNames();
		tempUser2 = bankApplication.inputLogin();
		tempUser.setUserName(tempUser2.getUserName());
		tempUser.setPassword(tempUser2.getPassword());

		return tempUser;
	}

	public void logout() {
		System.out.println("Thank you for using this application.");
		System.out.println("Goodbye.");
		System.exit(1);
	}

	public char displayOptions() {
		char choiceMade;

		// Manager and Teller permissions are not implemented at this time
		// So all users at this point will be either a User or a Super
		if (isSuper) {
			System.out.println("U to Manage All Users");
		}

		System.out.println("P to Manage Your User Profile");
		System.out.println("A to Manage Your Accounts");
		System.out.println("L to Logout");
		System.out.println("Enter Selection: ");

		choiceMade = sc.nextLine().charAt(0);
		return choiceMade;
	}

	public String displayOptions(List<String> options, boolean canLogout) {
		String choiceMade = null;

		for (String s : options) {
			System.out.println(s);
		}

		if (canLogout) {
			optionsCloser();
		}

		choiceMade = sc.nextLine();

		return choiceMade;
	}

	public String optionLooper(List<String> options, boolean canLogout) {
		String choice = null;
		String token = null;
		isValid = false;
		StringTokenizer st = new StringTokenizer("");

		while (!isValid) {
			choice = displayOptions(options, canLogout);
			st = new StringTokenizer(validSelection, ",");
			while (st.hasMoreTokens()) {
				token = st.nextToken();
				if (choice.equals(token)) {
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				System.out.println("Entry wasn't valid.");
			}
		}

		return choice;
	}

	public void optionsCloser() {
		System.out.println("R to Return to the Main menu");
		System.out.println("L to Logout");
		System.out.println("Enter Selection: ");
	}

	public void manageUsers() {
		List<String> options = new ArrayList<String>();
		String chosen = null;

		try {
			usersList = UserService.getUserService().findAllUsers();

			options.add("0 to create a new user");
			validSelection = "0,";

			for (User u : usersList) {
				System.out.println(u);
				options.add(usersList.indexOf(u) + 1 + " to edit " + u.getUserName());
				validSelection += usersList.indexOf(u) + 1 + ",";
			}

			validSelection += "R,L";

			chosen = optionLooper(options, true);

			switch (chosen) {
			case "0":
				User newUser = newLogin();
				newUser.setUpdatedBy(userDetails.getId());
				UserService.getUserService().registerUser(newUser);
				manageUsers();
				break;
			case "R":
				bankMain();
				break;
			case "L":
				logout();
				break;
			default:
				int editId = Integer.parseInt(chosen) - 1;
				User editUser = UserService.getUserService().findUser(usersList.get(editId), "ID");

				chosen = userModifyOrDelete(editUser);

				if (chosen.equals("L") || chosen.equals("Y")) {
					logout();
				} else if (chosen.equals("N")) {
					System.out.println("User Deletion Canceled.");
				}
				manageUsers();
			}
		} catch (Exception e) {
			log.error("Unable to Manage Users due to a system error. Please contact a system administrator.");
		}
	}

	public void manageProfile() {
		String chosen;
		System.out.println(UserService.getUserService().findUser(userDetails, "Name"));
		chosen = userModifyOrDelete(userDetails);

		if (chosen.equals("L") || chosen.equals("Y")) {
			logout();
		} else if (chosen.equals("N")) {
			System.out.println("User Deletion Canceled.");
		} else if (chosen.equals("R")) {
			bankMain();
		}
		manageProfile();
	}

	public String userModifyOrDelete(User editUser) {
		List<String> options = new ArrayList<String>();
		String chosen;

		validSelection = "M,D,R,L";

		options.add("M to Modify " + editUser.getUserName());
		options.add("D to Delete " + editUser.getUserName());

		chosen = optionLooper(options, true);

		if (chosen.equals("M")) {
			modifyUser(editUser);
			if (userDetails.getId() == editUser.getId()) {
				userDetails = UserService.getUserService().findUser(editUser, "ID");
			}
		} else if (chosen.equals("D")) {
			if (userDetails.getId() == editUser.getId()) {
				options.clear();
				validSelection = "Y,N,R,L";
				System.out.println("Deleting your own account will kick you from the system.");
				System.out.println("If you proceed you won't be able to log in again on this account.");
				options.add("Y to Continue");
				options.add("N to Cancel");

				chosen = optionLooper(options, true);

				if (chosen.equals("Y")) {
					UserService.getUserService().removeUser(editUser);
					logout();
					System.exit(1);
				} else if (chosen.equals("R")) {
					bankMain();
				}
			} else {
				UserService.getUserService().removeUser(editUser);
			}
		} else if (chosen.equals("R")) {
			bankMain();
		}
		return chosen;
	}

	public void modifyUser(User editUser) {
		User tempUser;
		tempUser = newLogin();
		tempUser.setId(editUser.getId());
		tempUser.setUpdatedBy(userDetails.getId());
		UserService.getUserService().modifyUser(tempUser);
	}

	public void manageAccounts() {
		List<String> options = new ArrayList<String>();
		String chosen = null;

		try {
			accountsList = AccountService.getAccountService().findUsersAccounts(userDetails);

			options.add("0 to create a new Account");
			validSelection = "0,";

			for (Account a : accountsList) {
				System.out.println(a);
				options.add(accountsList.indexOf(a) + 1 + " to edit " + a.getNickName());
				validSelection += accountsList.indexOf(a) + 1 + ",";
			}

			validSelection += "R,L";

			chosen = optionLooper(options, true);

			switch (chosen) {
			case "0":
				Account newAccount = newAccount();
				newAccount.setUpdatedBy(userDetails.getId());
				newAccount.setUserID(userDetails.getId());
				AccountService.getAccountService().registerAccount(newAccount);
				manageAccounts();
				break;
			case "R":
				bankMain();
				break;
			case "L":
				logout();
				break;
			default:
				int editId = Integer.parseInt(chosen) - 1;
				Account editAccount = AccountService.getAccountService().findAccount(accountsList.get(editId));

				chosen = accountOptions(editAccount);

				if (chosen.equals("L") || chosen.equals("Y")) {
					logout();
				} else if (chosen.equals("N")) {
					System.out.println("Account Deletion Canceled.");
				}
				manageAccounts();
			}
		} catch (Exception e) {
			log.error("Unable to Manage Accounts due to a system error. Please contact a system administrator.");
		}
	}

	public String accountOptions(Account editAccount) {
		List<String> options = new ArrayList<String>();
		String chosen;
		int amount;

		System.out.println(editAccount);
		editAccount.setUpdatedBy(userDetails.getId());
		validSelection = "S,W,T,D,R,L";

		options.add("S to Deposit to " + editAccount.getNickName());	
		options.add("W to Withdraw from " + editAccount.getNickName());		
		options.add("T to view Transaction History for " + editAccount.getNickName());
		options.add("D to Delete " + editAccount.getNickName());

		chosen = optionLooper(options, true);

		switch (chosen) {
		case "S":
			System.out.println("Amount to Deposit:");
			amount = editAccount.getBalance() + Integer.parseInt(sc.nextLine());
			editAccount.setBalance(amount);
			AccountService.getAccountService().modifyAccount(editAccount);
			break;
		case "W":
			isValid = false;
			
			while (!isValid) {
				System.out.println("Amount to Withdraw:");
				amount = Integer.parseInt(sc.nextLine());
				if (amount <= editAccount.getBalance()){
					amount = editAccount.getBalance() - amount;
					isValid = true;
					editAccount.setBalance(amount);
				} else {
					System.out.println("The requested Withdrawn amount cannot exceed the current balance.");
					System.out.println("Please try again.");
				}						
			}
			
			AccountService.getAccountService().modifyAccount(editAccount);
			break;
		case "T":
			List<Transaction> transactions = AccountService.getAccountService().getTransactions(editAccount);
			if (transactions.size() > 0) {
				for (Transaction t : transactions) {
					System.out.println(t);
				}
			} else {
				System.out.println("There are no transactions for this account.");
			}			
			break;
		case "D":
			if (editAccount.getBalance() == 0){
				AccountService.getAccountService().removeAccount(editAccount);
			} else {
				System.out.println("Account Balance must be 0 to be Deleted.");
				accountOptions(editAccount);
			}
			break;
		case "R":
			bankMain();
			break;
		}
		
		return chosen;
	}

	public Account newAccount() {
		String nickName = null;
		String balance;
		Account tempAccount = new Account();
		List<String> options = new ArrayList<String>();
		List<String> accountTypes = new ArrayList<String>();
		String chosen = null;
		String[] accountTypesSplit;

		System.out.println("Nick Name: ");
		nickName = sc.nextLine();
		System.out.println("Balance: ");
		balance = sc.nextLine();

		validSelection = "";

		accountTypes = AccountService.getAccountService().getAccountTypes();

		for (String s : accountTypes) {
			accountTypesSplit = s.split(";");
			options.add(accountTypesSplit[0] + " for " + accountTypesSplit[1]);
			validSelection += accountTypesSplit[0] + ",";
		}

		chosen = optionLooper(options, false);

		tempAccount.setNickName(nickName);
		tempAccount.setBalance(Integer.parseInt(balance));
		tempAccount.setType(Integer.parseInt(chosen));

		return tempAccount;
	}
}