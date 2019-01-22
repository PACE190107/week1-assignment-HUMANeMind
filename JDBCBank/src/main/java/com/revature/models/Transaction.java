package com.revature.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7404893430151498521L;
	private int id;	
	private int accountID;
	private int previousBalance;
	private int newBalance;
	private int userID;
	private LocalDateTime eventTime;	
	
	public Transaction() {
		super();
	}
	

	public Transaction(int id, int accountID, int previousBalance, int newBalance, int userID,
			LocalDateTime eventTime) {
		super();
		this.id = id;
		this.accountID = accountID;
		this.previousBalance = previousBalance;
		this.newBalance = newBalance;
		this.userID = userID;
		this.eventTime = eventTime;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getAccountID() {
		return accountID;
	}


	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}


	public int getPreviousBalance() {
		return previousBalance;
	}


	public void setPreviousBalance(int previousBalance) {
		this.previousBalance = previousBalance;
	}


	public int getNewBalance() {
		return newBalance;
	}


	public void setNewBalance(int newBalance) {
		this.newBalance = newBalance;
	}


	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}


	public LocalDateTime getEventTime() {
		return eventTime;
	}


	public void setEventTime(LocalDateTime eventTime) {
		this.eventTime = eventTime;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountID;
		result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
		result = prime * result + id;
		result = prime * result + newBalance;
		result = prime * result + previousBalance;
		result = prime * result + userID;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (accountID != other.accountID)
			return false;
		if (eventTime == null) {
			if (other.eventTime != null)
				return false;
		} else if (!eventTime.equals(other.eventTime))
			return false;
		if (id != other.id)
			return false;
		if (newBalance != other.newBalance)
			return false;
		if (previousBalance != other.previousBalance)
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [accountID=" + accountID + ", previousBalance=" + previousBalance
				+ ", newBalance=" + newBalance + ", eventTime=" + eventTime + "]";
	}	
}
