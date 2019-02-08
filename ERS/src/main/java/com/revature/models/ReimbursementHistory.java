package com.revature.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReimbursementHistory implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7404893430151498521L;
	private int id;	
	private int reimbursementId;
	private int previousStatus;
	private int newStatus;
	private int updatedBy;
	private LocalDateTime eventTime;	
	
	public ReimbursementHistory() {
		super();
	}

	public ReimbursementHistory(int id, int reimbursementId, int previousStatus, int newStatus, int updatedBy,
			LocalDateTime eventTime) {
		super();
		this.id = id;
		this.reimbursementId = reimbursementId;
		this.previousStatus = previousStatus;
		this.newStatus = newStatus;
		this.updatedBy = updatedBy;
		this.eventTime = eventTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReimbursementId() {
		return reimbursementId;
	}

	public void setReimbursementId(int reimbursementId) {
		this.reimbursementId = reimbursementId;
	}

	public int getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(int previousStatus) {
		this.previousStatus = previousStatus;
	}

	public int getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(int newStatus) {
		this.newStatus = newStatus;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalDateTime eventTime) {
		this.eventTime = eventTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
		result = prime * result + id;
		result = prime * result + newStatus;
		result = prime * result + previousStatus;
		result = prime * result + reimbursementId;
		result = prime * result + updatedBy;
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
		ReimbursementHistory other = (ReimbursementHistory) obj;
		if (eventTime == null) {
			if (other.eventTime != null)
				return false;
		} else if (!eventTime.equals(other.eventTime))
			return false;
		if (id != other.id)
			return false;
		if (newStatus != other.newStatus)
			return false;
		if (previousStatus != other.previousStatus)
			return false;
		if (reimbursementId != other.reimbursementId)
			return false;
		if (updatedBy != other.updatedBy)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursmentHistory [id=" + id + ", reimbursementId=" + reimbursementId + ", previousStatus="
				+ previousStatus + ", newStatus=" + newStatus + ", updatedBy=" + updatedBy + ", eventTime=" + eventTime
				+ "]";
	}	
}
