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
		
		System.out.println("Welcome to the JDBCBank Application!");
		// Chose to login, create account, or logout
		loginSuccessful = bankApplication.createOrLogin();

		// Login or creation successful so proceed with the rest of the application
		if (loginSuccessful) {
			userDetails = UserService.getUserService().findUser(userDetails, "Name");
			System.out.println("Welcome " + userDetails.getFirstName() + ".");
			permissions = UserService.getUserService().findPermissions(userDetails);

			bankApplication.bankMain();
		}
	}

	// The main menu for this application
	public void bankMain() {		
		isValid = false;
		char entered = ' ';		

		//Loop through the menu choices until a valid entry is made
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

		// Valid entry was made so determine which entry the user made
		switch (entered) {
		case 'U':
			if (isSuper) {
				System.out.println("-------------------------");
				System.out.println("Manage Users:");
				bankApplication.manageUsers();
			} else {
				System.out.println("You shouldn't be able to see this message. Please contact a system administrator.");
			}
			break;
		case 'P':
			System.out.println("-------------------------");
			System.out.println("Manage Your Profile:");
			bankApplication.manageProfile();
			break;
		case 'A':
			bankApplication.manageAccounts();
			break;
		case 'L':
			bankApplication.logout();
			break;
		}
	}

	// Chose to login, create account, or logout
	public boolean createOrLogin() {
		String typeOfLogin;
		boolean loginCondition = false;

		System.out.println("1 to Login");
		System.out.println("2 to Create a New User");
		System.out.println("Enter anything else to Exit");
		System.out.println("Enter Selection: ");

		typeOfLogin = sc.nextLine();

		switch (typeOfLogin) {
		case "1":
			//Loop through the login process until a valid username and password are entered
			while (!loginCondition) {
				userDetails = inputLogin();

				// Validates that the entered password and username matches a username and password combination in the database
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
				// Keep looping through this until a user is created successfully
				// Username must be unique, if they aren't you can't make that user
				userDetails = newLogin();
				loginCondition = UserService.getUserService().registerUser(userDetails);
			}
			return loginCondition;
		default:
			logout();
			return false;
		}
	}

	// Method to get and return user's first and last name
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

	// Method to get and return user's username and password
	public User inputLogin() {
		String userName = null;
		String password = null;
		User tempUser = new User();

		System.out.println("-------------------------");
		System.out.println("User Name: ");
		userName = sc.nextLine();
		System.out.println("Password: ");
		password = sc.nextLine();

		tempUser.setUserName(userName);
		tempUser.setPassword(password);

		return tempUser;
	}

	// Method to get user's full details
	public User newLogin() {
		User tempUser = new User();
		User tempUser2 = new User();

		tempUser = bankApplication.inputNames();
		tempUser2 = bankApplication.inputLogin();
		tempUser.setUserName(tempUser2.getUserName());
		tempUser.setPassword(tempUser2.getPassword());

		return tempUser;
	}

	// Logs the user out of the application
	public void logout() {
		System.out.println("Thank you for using this application.");
		System.out.println("Goodbye.");
		System.exit(1);
	}

	// Displays the options for the main program.
	// The variable to hold the valid options is
	public char displayOptions() {
		char choiceMade;

		// All users can manage their own profile and all their own accounts
		validSelection = "PAL";
		System.out.println("-------------------------");
		// Manager and Teller permissions are not implemented at this time
		// So all users at this point will be either a User or a Super
		if (permissions.contains("Super")) {
			System.out.println("U to Manage All Users");
			isSuper = true;
			validSelection += "U";
		}
		
		System.out.println("What would you like to do?");
		System.out.println("P to Manage Your User Profile");
		System.out.println("A to Manage Your Accounts");
		System.out.println("L to Logout");
		System.out.println("Enter Selection: ");

		choiceMade = sc.nextLine().charAt(0);
		return choiceMade;
	}

	// Display the options for all the other menus other than the main
	public String displayOptions(List<String> options, boolean canLogout) {
		String choiceMade = null;

		System.out.println("-------------------------");
		for (String s : options) {
			System.out.println(s);
		}

		if (canLogout) {
			optionsCloser();
		}

		choiceMade = sc.nextLine();

		return choiceMade;
	}

	// Loops the current displayed options until a valid one is entered
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

	// The closing options that are a part of every menu after the main menu
	public void optionsCloser() {
		System.out.println("R to Return to the Main menu");
		System.out.println("L to Logout");
		System.out.println("Enter Selection: ");
	}

	// The manage Users menu logic
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

				chosen = userOptions(editUser);

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

	// The manage Profile menu logic
	public void manageProfile() {
		String chosen;
		System.out.println(UserService.getUserService().findUser(userDetails, "Name"));
		chosen = userOptions(userDetails);

		if (chosen.equals("L") || chosen.equals("Y")) {
			logout();
		} else if (chosen.equals("N")) {
			System.out.println("User Deletion Canceled.");
		} else if (chosen.equals("R")) {
			bankMain();
		}
		manageProfile();
	}

	// Used to determine the action being taken on an existing user
	public String userOptions(User editUser) {
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
				System.out.println("Deleting your own account will remove you from the system.");
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

	// Used to modify the selected user
	public void modifyUser(User editUser) {
		User tempUser;
		tempUser = newLogin();
		tempUser.setId(editUser.getId());
		tempUser.setUpdatedBy(userDetails.getId());
		UserService.getUserService().modifyUser(tempUser);
	}

	// The manage Account menu logic
	public void manageAccounts() {
		List<String> options = new ArrayList<String>();
		String chosen = null;

		try {
			accountsList = AccountService.getAccountService().findUsersAccounts(userDetails);

			options.add("0 to create a new Account");
			validSelection = "0,";
			
			System.out.println("Manage " + userDetails.getFirstName() + "'s accounts:");
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
			e.printStackTrace();
			log.error("Unable to Manage Accounts due to a system error. Please contact a system administrator.");
		}
	}

	// Used to determine the action being taken on an existing account
	public String accountOptions(Account editAccount) {
		List<String> options = new ArrayList<String>();
		String chosen;
		double amount;

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
			
			isValid = false;
			
			while (!isValid) {
				amount = Double.parseDouble(sc.nextLine());
				
				if (amount < 0) {
					System.out.println("You cannot Deposit a negative ammount.");
					System.out.println("Try again:");
					continue;
				}
				else {
					amount = editAccount.getBalance() + amount;
					editAccount.setBalance(amount);
					isValid = true;
				}
			}				
			
			AccountService.getAccountService().modifyAccount(editAccount);
			break;
		case "W":
			isValid = false;
			
			while (!isValid) {
				System.out.println("Amount to Withdraw:");
				amount = Double.parseDouble(sc.nextLine());
				
				if (amount < 0) {
					System.out.println("You cannot Withdraw a negative ammount.");
					System.out.println("Try again:");
					continue;
				}
				
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
				System.out.println("Transactions for " + transactions.get(0).getAccountID());
				for (Transaction t : transactions) {
					System.out.println(t);
				}
			} else {
				System.out.println("There are no transactions for this account.");
			}
			System.out.println("-------------------------");
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

	// Method to create a new Account
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