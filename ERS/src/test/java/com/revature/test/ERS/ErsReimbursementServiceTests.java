package com.revature.test.ERS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementHistory;
import com.revature.services.ReimbursementServiceImplementation;

public class ErsReimbursementServiceTests {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/*******************************************************************
	 * Test Reimbursement
	 ******************************************************************/

	@Test
	public void registerReimbursement() {
		Reimbursement newReimbursement = new Reimbursement(100, 1, "Pending", "NewTest", 101, "E1", 102, "E2", 100.00,
				LocalDateTime.parse("2019-01-21T21:38:48"), "None", LocalDate.parse("2019-02-07"));

		assertTrue(
				ReimbursementServiceImplementation.getReimbursementService().registerReimbursement(newReimbursement));
		ReimbursementServiceImplementation.getReimbursementService().removeReimbursement(newReimbursement);
	}

	@Ignore
	@Test
	public void modifyReimbursement() {
		Reimbursement foundReimbursement = new Reimbursement();
		foundReimbursement.setId(1005);
		foundReimbursement = ReimbursementServiceImplementation.getReimbursementService()
				.findReimbursement(foundReimbursement, "ID");

		foundReimbursement.setStatus(1);
		assertTrue(
				ReimbursementServiceImplementation.getReimbursementService().modifyReimbursement(foundReimbursement));

		foundReimbursement.setStatus(2);
		ReimbursementServiceImplementation.getReimbursementService().modifyReimbursement(foundReimbursement);
	}

	@Test
	public void getAllReimbursements() {
		List<Reimbursement> allReimbursements = new ArrayList<Reimbursement>();

		allReimbursements.add(new Reimbursement(1021, 1, "Pending", "nothing", 101, "E1", 101, "E1", 50000.0,
				LocalDateTime.parse("2019-02-08T16:05:16"), "games", LocalDate.parse("2019-01-02")));
		
		List<Reimbursement> systemReimbursements = ReimbursementServiceImplementation.getReimbursementService()
				.getAllReimbursements(1);
		
		// Because this will keep growing during testing just test that the oldest
		// History entry matches
		systemReimbursements.subList(0, systemReimbursements.size() - 1).clear();
		assertEquals(allReimbursements, systemReimbursements);
	}

	@Test
	public void getEmployeesReimbursements() {
		Employee currentEmployee = new Employee();
		currentEmployee.setId(102);

		List<Reimbursement> allReimbursements = new ArrayList<Reimbursement>();
		allReimbursements.add(new Reimbursement(1006, 1, "Pending", "test", 101, "E1", 102, "E2", 100.00,
				LocalDateTime.parse("2019-02-04T14:03:17"), "None", LocalDate.parse("2019-02-07")));

		List<Reimbursement> systemReimbursements = ReimbursementServiceImplementation.getReimbursementService()
				.findEmployeesReimbursements(currentEmployee);
		// Because this will keep growing during testing just test that the oldest
		// History entry matches
		systemReimbursements.subList(0, systemReimbursements.size() - 1).clear();
		assertEquals(allReimbursements, systemReimbursements);
	}

	@Test
	public void getReimbursementByID() {
		Reimbursement toFind = new Reimbursement(1006, 1, "Pending", "test", 101, "E1", 102, "E2", 100.00,
				LocalDateTime.parse("2019-02-04T14:03:17"), "None", LocalDate.parse("2019-02-07"));

		assertEquals(toFind,
				ReimbursementServiceImplementation.getReimbursementService().findReimbursement(toFind, "ID"));
	}

	@Test
	public void getReimbursementHistoryByID() {
		Reimbursement toFind = new Reimbursement();
		toFind.setId(1005);

		List<ReimbursementHistory> history = new ArrayList<ReimbursementHistory>();
		history.add(new ReimbursementHistory(1, 1005, 1, 2, 101, LocalDateTime.parse("2019-02-05T03:00:39")));

		// Because this will keep growing during testing just test that the oldest
		// History entry matches
		List<ReimbursementHistory> history2 = ReimbursementServiceImplementation.getReimbursementService()
				.getHistory(toFind);
		history2.subList(0, history2.size() - 1).clear();

		assertEquals(history, history2);
	}

	@Test
	public void testStatus() {
		List<String> status = new ArrayList<String>();
		status.add("1;Pending");
		status.add("2;Approved");
		status.add("3;Denied");

		assertEquals(status, ReimbursementServiceImplementation.getReimbursementService().getReimbursementStatus());
	}
}
