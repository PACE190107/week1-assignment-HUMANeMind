package com.revature.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reimbursement implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4356417056925542109L;
	private int id;	
	private int status;
	private String statusName;
	private String reciept;
	private int updaterID;
	private String updatedBy;
	private int submitterID;
	private String submittedBy;
	private double amount;
	private LocalDateTime submittedOn;
	private String expense;
	private LocalDate expenseDate;
	
	
	public Reimbursement() {
		super();
	}


	public Reimbursement(int id, int status, String statusName, String reciept, int updaterID, String updatedBy,
			int submitterID, String submittedBy, double amount, LocalDateTime submittedOn, String expense,
			LocalDate expenseDate) {
		super();
		this.id = id;
		this.status = status;
		this.statusName = statusName;
		this.reciept = reciept;
		this.updaterID = updaterID;
		this.updatedBy = updatedBy;
		this.submitterID = submitterID;
		this.submittedBy = submittedBy;
		this.amount = amount;
		this.submittedOn = submittedOn;
		this.expense = expense;
		this.expenseDate = expenseDate;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getStatusName() {
		return statusName;
	}


	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public String getReciept() {
		return reciept;
	}


	public void setReciept(String reciept) {
		this.reciept = reciept;
	}


	public int getUpdaterID() {
		return updaterID;
	}


	public void setUpdaterID(int updaterID) {
		this.updaterID = updaterID;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public int getSubmitterID() {
		return submitterID;
	}


	public void setSubmitterID(int submitterID) {
		this.submitterID = submitterID;
	}


	public String getSubmittedBy() {
		return submittedBy;
	}


	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public LocalDateTime getSubmittedOn() {
		return submittedOn;
	}


	public void setSubmittedOn(LocalDateTime submittedOn) {
		this.submittedOn = submittedOn;
	}


	public String getExpense() {
		return expense;
	}


	public void setExpense(String expense) {
		this.expense = expense;
	}


	public LocalDate getExpenseDate() {
		return expenseDate;
	}


	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((expense == null) ? 0 : expense.hashCode());
		result = prime * result + ((expenseDate == null) ? 0 : expenseDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((reciept == null) ? 0 : reciept.hashCode());
		result = prime * result + status;
		result = prime * result + ((statusName == null) ? 0 : statusName.hashCode());
		result = prime * result + ((submittedBy == null) ? 0 : submittedBy.hashCode());
		result = prime * result + ((submittedOn == null) ? 0 : submittedOn.hashCode());
		result = prime * result + submitterID;
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result + updaterID;
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
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (expense == null) {
			if (other.expense != null)
				return false;
		} else if (!expense.equals(other.expense))
			return false;
		if (expenseDate == null) {
			if (other.expenseDate != null)
				return false;
		} else if (!expenseDate.equals(other.expenseDate))
			return false;
		if (id != other.id)
			return false;
		if (reciept == null) {
			if (other.reciept != null)
				return false;
		} else if (!reciept.equals(other.reciept))
			return false;
		if (status != other.status)
			return false;
		if (statusName == null) {
			if (other.statusName != null)
				return false;
		} else if (!statusName.equals(other.statusName))
			return false;
		if (submittedBy == null) {
			if (other.submittedBy != null)
				return false;
		} else if (!submittedBy.equals(other.submittedBy))
			return false;
		if (submittedOn == null) {
			if (other.submittedOn != null)
				return false;
		} else if (!submittedOn.equals(other.submittedOn))
			return false;
		if (submitterID != other.submitterID)
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updaterID != other.updaterID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", status=" + status + ", statusName=" + statusName + ", reciept=" + reciept
				+ ", updaterID=" + updaterID + ", updatedBy=" + updatedBy + ", submitterID=" + submitterID
				+ ", submittedBy=" + submittedBy + ", amount=" + amount + ", submittedOn=" + submittedOn + ", expense="
				+ expense + ", expenseDate=" + expenseDate + "]";
	}	
}