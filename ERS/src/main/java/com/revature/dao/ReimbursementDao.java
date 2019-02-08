package com.revature.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementHistory;
import com.revature.models.Employee;

public interface ReimbursementDao {
	public Object process(HttpServletRequest request, HttpServletResponse response);

	public boolean insertReimbursement(Reimbursement insertedReimbursement);
	public boolean updateReimbursement(Reimbursement updatedReimbursement);
	public List<Reimbursement> getAllReimbursements(int status);
	public List<Reimbursement> getAllReimbursementsForEmployee(Employee currentUser);
	public Reimbursement getReimbursement(Reimbursement reimbursementToFind, String by);
	public List<String> getReimbursementStatus();
	public List<ReimbursementHistory> getReimbursementHistory(Reimbursement currentReimbursement);
}