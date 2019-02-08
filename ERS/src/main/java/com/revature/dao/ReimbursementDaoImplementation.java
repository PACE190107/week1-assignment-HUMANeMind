package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementHistory;
import com.revature.models.Employee;
import com.revature.util.JDBCConnectionUtil;

// Dao class implements DAO interface
// DAO class retrieves data from database
public class ReimbursementDaoImplementation implements ReimbursementDao {
	private static ReimbursementDaoImplementation reimbursementDao;
	final static Logger log = Logger.getLogger(ReimbursementDaoImplementation.class);

	private ReimbursementDaoImplementation() {
	}

	public static ReimbursementDaoImplementation getReimbursementDao() {
		if (reimbursementDao == null) {
			reimbursementDao = new ReimbursementDaoImplementation();
		}
		return reimbursementDao;
	}

	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertReimbursement(Reimbursement insertedReimbursement) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "{CALL INSERT_REIMBURSEMENT(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement cs = conn.prepareCall(sql);

			if (insertedReimbursement.getId() > 0) {
				cs.setInt(1, insertedReimbursement.getId());
			} else {
				cs.setString(1, null);
			}

			cs.setInt(2, insertedReimbursement.getStatus());
			cs.setString(3, insertedReimbursement.getReciept());
			cs.setInt(4, insertedReimbursement.getUpdaterID());
			cs.setInt(5, insertedReimbursement.getSubmitterID());
			cs.setDouble(6, insertedReimbursement.getAmount());
			cs.setTimestamp(7, Timestamp.valueOf(insertedReimbursement.getSubmittedOn()));
			cs.setString(8, insertedReimbursement.getExpense());
			cs.setDate(9, Date.valueOf(insertedReimbursement.getExpenseDate()));

			if (cs.executeUpdate() > 0) {
				System.out.println("Reimbursement Created successfully.");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Unable to create Reimbursement due to a system error. Please contact a system administrator.");
		}

		return false;
	}

	@Override
	public boolean updateReimbursement(Reimbursement updatedReimbursement) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "UPDATE REIMBURSEMENT " + "SET STATUS = ?, " + "UPDATED_BY = ? "
					+ "WHERE REIMBURSEMENT_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, updatedReimbursement.getStatus());
			ps.setInt(2, updatedReimbursement.getUpdaterID());
			ps.setInt(3, updatedReimbursement.getId());

			if (ps.executeUpdate() > 0) {
				System.out.println("Reimbursement successfully altered.");
				return true;
			}

		} catch (SQLException e) {
			log.error("Unable to update Reimbursement due to a system error. Please contact a system administrator.");
		}

		return false;
	}

	@Override
	public List<Reimbursement> getAllReimbursements(int status) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Reimbursements
			String sql = "SELECT * FROM REIMBURSEMENT_FULL_VW WHERE STATUS_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, status);
			ResultSet rs = ps.executeQuery();

			List<Reimbursement> allReimbursements = new ArrayList<Reimbursement>();

			// Return all rows in the results
			while (rs.next()) {
				allReimbursements.add(new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getDouble(9),
						rs.getTimestamp(10).toLocalDateTime(), rs.getString(11), rs.getDate(12).toLocalDate()));
			}

			return allReimbursements;

		} catch (SQLException e) {
			log.error(
					"Unable to return all Reimbursements due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	@Override
	public List<Reimbursement> getAllReimbursementsForEmployee(Employee currentEmployee) {
		List<Reimbursement> allReimbursements = new ArrayList<Reimbursement>();

		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Reimbursements for this employee
			String sql = "SELECT * FROM REIMBURSEMENT_FULL_VW WHERE SUBMITTED_BY = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, currentEmployee.getEmployeeID());

			ResultSet rs = ps.executeQuery();

			// Return all rows in the results
			while (rs.next()) {
				allReimbursements.add(new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getDouble(9),
						rs.getTimestamp(10).toLocalDateTime(), rs.getString(11), rs.getDate(12).toLocalDate()));
			}

			return allReimbursements;

		} catch (SQLException e) {
			System.out.println("Unable to return all Reimbursements for " + currentEmployee.getUserName()
					+ " due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	@Override
	public Reimbursement getReimbursement(Reimbursement reimbursementToFind, String by) {
		PreparedStatement ps;
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Query to find a specific reimbursement
			if (by == "Submitter") {
				String sql = "SELECT * FROM REIMBURSEMENT_FULL_VW WHERE SUBMITTED_BY = ? ORDER_BY SUBMITTED_ON DESC LIMIT 1";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, reimbursementToFind.getSubmitterID());
			} else {
				String sql = "SELECT * FROM REIMBURSEMENT_FULL_VW WHERE REIMBURSEMENT_ID = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, reimbursementToFind.getId());
			}
			
			ResultSet rs = ps.executeQuery();
			if (rs.getFetchSize() > 0) {
				// Store the results
				while (rs.next()) {
					reimbursementToFind = new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3),
							rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getString(8),
							rs.getDouble(9), rs.getTimestamp(10).toLocalDateTime(), rs.getString(11),
							rs.getDate(12).toLocalDate());
				}
				return reimbursementToFind;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.error(
					"Unable to return this Reimbursement due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	@Override
	public List<String> getReimbursementStatus() {
		List<String> status = new ArrayList<String>();

		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Reimbursement Status
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM STATUS ORDER BY STATUS_ID";

			ResultSet rs = stmt.executeQuery(sql);

			// Return all rows in the results
			while (rs.next()) {
				status.add(rs.getInt(1) + ";" + rs.getString(2));
			}

			return status;

		} catch (SQLException e) {
			log.error(
					"Unable to return all Reimbursement Status due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	@Override
	public List<ReimbursementHistory> getReimbursementHistory(Reimbursement currentReimbursement) {
		List<ReimbursementHistory> history = new ArrayList<ReimbursementHistory>();

		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			// Create Query to return All Reimbursement Status
			String sql = "SELECT * FROM REIMBURSEMENT_LOG WHERE REIMBURSEMENT_ID = ? ORDER BY EVENT_TIME DESC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, currentReimbursement.getId());

			ResultSet rs = ps.executeQuery();

			// Return all rows in the results
			while (rs.next()) {

				history.add(new ReimbursementHistory(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
						rs.getInt(5), rs.getTimestamp(6).toLocalDateTime()));
			}
			return history;

		} catch (SQLException e) {
			log.error(
					"Unable to return all Reimbursement History due to a system error. Please contact a system administrator.");
		}

		return null;
	}

	public boolean deleteReimbursement(Reimbursement currentReimbursement) {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "{CALL DELETE_REIMBURSEMENT(?)}";
			CallableStatement cs = conn.prepareCall(sql);

			cs.setInt(1, currentReimbursement.getId());

			if (cs.executeUpdate() > 0) {
				System.out.println("Reimbursement successfully Deleted.");
				return true;
			}

		} catch (SQLException e) {
			log.error("Unable to delete Reimbursement due to a system error. Please contact a system administrator.");
		}

		return false;
	}
}