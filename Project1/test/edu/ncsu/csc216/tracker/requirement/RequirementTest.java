package edu.ncsu.csc216.tracker.requirement;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;
import edu.ncsu.csc216.tracker.xml.Req;

/**
 * Test the Requirement objects methods, including FSM transitions
 * 
 * @author troy1
 *
 */
public class RequirementTest {

	/**
	 * testing the Requirement class standard workflow and
	 * UnsupportedOperationException checks
	 */
	@Test
	public void testRequirement() {
		Requirement r = new Requirement(null, null);
		assertNotNull(r);
		Req req = new Req();

		Requirement test = new Requirement("testing", "testtest");
		req = test.getXMLReq();
		r = new Requirement(req);
		try {
			Command c = new Command(CommandValue.ASSIGN, null, null, 0, null, "developer", null);
			test.update(c);
		} catch (UnsupportedOperationException e) {
			e.getStackTrace();
			assertEquals("testing", test.getSummary());
			assertEquals("testtest", test.getAcceptanceTestId());
		}

		Command c = new Command(CommandValue.ACCEPT, null, null, 1, "5 hour", null, null);
		test.update(c);
		assertEquals("Accepted", test.getXMLReq().getState());
		req = test.getXMLReq();
		r = new Requirement(req);

		try {
			c = new Command(CommandValue.ACCEPT, null, null, 1, "5 hours", "developer", null);
			test.update(c);
		} catch (UnsupportedOperationException e) {
			e.getStackTrace();
			assertEquals("testing", test.getSummary());
			assertEquals("testtest", test.getAcceptanceTestId());
			assertEquals(1, test.getPriority());
			assertEquals("5 hour", test.getEstimate());
		}

		c = new Command(CommandValue.ASSIGN, null, null, 0, null, "developer", null);
		test.update(c);
		assertEquals("Working", test.getXMLReq().getState());
		req = test.getXMLReq();
		r = new Requirement(req);

		try {
			c = new Command(CommandValue.PASS, null, null, 0, null, null, null);
			test.update(c);
		} catch (UnsupportedOperationException e) {
			e.getStackTrace();
		}

		c = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test.update(c);
		assertEquals("Completed", test.getXMLReq().getState());
		req = test.getXMLReq();
		r = new Requirement(req);
		try {
			c = new Command(CommandValue.ACCEPT, null, null, 1, "5 hours", "developer", null);
			test.update(c);
		} catch (UnsupportedOperationException e) {
			e.getStackTrace();
		}

		c = new Command(CommandValue.PASS, null, null, 0, null, null, null);
		test.update(c);
		assertEquals("Verified", test.getXMLReq().getState());
		req = test.getXMLReq();
		r = new Requirement(req);
		try {
			c = new Command(CommandValue.PASS, null, null, 0, null, null, null);
			test.update(c);
		} catch (UnsupportedOperationException e) {
			e.getStackTrace();
		}

		assertEquals("testtest", test.getAcceptanceTestId());
		assertEquals("developer", test.getDeveloper());
		assertEquals("5 hour", test.getEstimate());
		assertEquals(1, test.getPriority());
		assertEquals(null, test.getRejectionReasonString());
		assertEquals("testing", test.getSummary());

		assertEquals("developer", test.getXMLReq().getDeveloper());
		// testing rejected
		c = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.TOO_LARGE);
		test.update(c);
		assertEquals("Rejected", test.getXMLReq().getState());
		assertEquals(Rejection.TOO_LARGE, test.getRejectionReason());

		try {
			c = new Command(CommandValue.PASS, null, null, 0, null, null, null);
			test.update(c);
		} catch (UnsupportedOperationException e) {
			e.getStackTrace();
		}

		Requirement n = new Requirement(test.getXMLReq());
		assertNotNull(n);

		Command c1 = new Command(CommandValue.REVISE, "summary", "testtest", 0, null, null, null);
		test.update(c1);
		int reqId = test.getRequirementId();
		assertNotNull(reqId);

		Requirement.setCounter(40);
		Requirement c40 = new Requirement(null, null);
		assertEquals(40, c40.getRequirementId());
	}

	/**
	 * testing the possible rejection reasons
	 */
	@Test
	public void testRequirementErrorReasons() {
		Requirement test2 = new Requirement("testing", "testtest");
		Command c = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.DUPLICATE);
		test2.update(c);
		assertEquals("Rejected", test2.getXMLReq().getState());
		assertEquals(Rejection.DUPLICATE, test2.getRejectionReason());

		Requirement test3 = new Requirement("testing", "testtest");
		Command c1 = new Command(CommandValue.ACCEPT, null, null, 1, "5 hour", null, null);
		test3.update(c1);
		c = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.OUT_OF_SCOPE);
		test3.update(c);
		assertEquals("Rejected", test3.getXMLReq().getState());
		assertEquals(Rejection.OUT_OF_SCOPE, test3.getRejectionReason());

		Requirement test4 = new Requirement("testing", "testtest");
		c1 = new Command(CommandValue.ACCEPT, null, null, 1, "5 hour", null, null);
		test4.update(c1);
		c = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.INAPPROPRIATE);
		test4.update(c);
		assertEquals("Rejected", test4.getXMLReq().getState());
		assertEquals(Rejection.INAPPROPRIATE, test4.getRejectionReason());

	}

	/**
	 * testing the Requirement class accept to rejection transition
	 */
	@Test
	public void testRequirementSubmitReject() {
		Requirement test1 = new Requirement("testing", "testtest");
		assertEquals("Submitted", test1.getState().getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, null, null, 1, "5 hour", null, null);
		test1.update(c1);
		assertEquals("Accepted", test1.getState().getStateName());
		c1 = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.INFEASIBLE);
		test1.update(c1);
		assertEquals("Rejected", test1.getXMLReq().getState());
		assertEquals(Rejection.INFEASIBLE, test1.getRejectionReason());
	}

	/**
	 * testing the Requirement class working to rejection transition
	 */
	@Test
	public void testRequirementWorkingReject() {
		Requirement test1 = new Requirement("testing", "testtest");
		assertEquals("Submitted", test1.getState().getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, null, null, 1, "5 hour", null, null);
		test1.update(c1);
		assertEquals("Accepted", test1.getState().getStateName());

		c1 = new Command(CommandValue.ASSIGN, null, null, 0, null, "developer", null);
		test1.update(c1);
		assertEquals("Working", test1.getXMLReq().getState());
		assertEquals("Working", test1.getState().getStateName());
		c1 = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.INFEASIBLE);
		test1.update(c1);
		assertEquals("Rejected", test1.getXMLReq().getState());
		assertEquals(Rejection.INFEASIBLE, test1.getRejectionReason());
	}

	/**
	 * testing the Requirement class working and completed transition
	 */
	@Test
	public void testRequirementWorkingOptions() {
		Requirement test1 = new Requirement("testing", "testtest");
		assertEquals("Submitted", test1.getState().getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, null, null, 1, "5 hour", null, null);
		test1.update(c1);
		assertEquals("Accepted", test1.getState().getStateName());

		c1 = new Command(CommandValue.ASSIGN, null, null, 0, null, "developer", null);
		test1.update(c1);
		assertEquals("Working", test1.getXMLReq().getState());

		c1 = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test1.update(c1);
		assertEquals("Completed", test1.getXMLReq().getState());

		c1 = new Command(CommandValue.FAIL, null, null, 0, null, null, null);
		test1.update(c1);
		assertEquals("Working", test1.getXMLReq().getState());

		c1 = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test1.update(c1);
		assertEquals("Completed", test1.getXMLReq().getState());

		c1 = new Command(CommandValue.ASSIGN, null, null, 0, null, "developor", null);
		test1.update(c1);
		assertEquals("Working", test1.getXMLReq().getState());

		c1 = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test1.update(c1);
		assertEquals("Completed", test1.getXMLReq().getState());
		assertEquals("Completed", test1.getState().getStateName());

		c1 = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.INFEASIBLE);
		test1.update(c1);
		assertEquals("Rejected", test1.getXMLReq().getState());
		assertEquals(Rejection.INFEASIBLE, test1.getRejectionReason());
		assertEquals("Rejected", test1.getState().getStateName());
	}

	/**
	 * testing the Requirement class submit to assign transition
	 */
	@Test
	public void testRequirementSubmitToAssign() {
		Requirement test1 = new Requirement("testing", "testtest");
		assertEquals("Submitted", test1.getState().getStateName());
		Command c1 = new Command(CommandValue.ACCEPT, null, null, 1, "5 hour", null, null);
		test1.update(c1);
		assertEquals("Accepted", test1.getState().getStateName());

		c1 = new Command(CommandValue.ASSIGN, null, null, 0, null, "developer", null);
		test1.update(c1);
		assertEquals("Working", test1.getXMLReq().getState());

		c1 = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test1.update(c1);
		assertEquals("Completed", test1.getXMLReq().getState());

		c1 = new Command(CommandValue.PASS, null, null, 0, null, null, null);
		test1.update(c1);
		assertEquals("Verified", test1.getXMLReq().getState());
		assertEquals("Verified", test1.getState().getStateName());

		c1 = new Command(CommandValue.ASSIGN, null, null, 0, null, "developer", null);
		test1.update(c1);
		assertEquals("Working", test1.getXMLReq().getState());
	}

	/**
	 * Testing setState and setRejectionReasonString
	 */
	@Test
	public void testSetStateAndSetRejectionReasonString() {
		Requirement test1 = new Requirement("testing", "testtest");
		Command c = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.DUPLICATE);
		test1.update(c);
		Req r = test1.getXMLReq();
		Requirement incommingTest1 = new Requirement(r);

		Requirement test2 = new Requirement("testing", "testtest");
		Command c2 = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.INFEASIBLE);
		test2.update(c2);
		Req r2 = test2.getXMLReq();
		Requirement incommingTest2 = new Requirement(r2);

		Requirement test3 = new Requirement("testing", "testtest");
		Command c3 = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.OUT_OF_SCOPE);
		test3.update(c3);
		Req r3 = test3.getXMLReq();
		Requirement incommingTest3 = new Requirement(r3);

		Requirement test4 = new Requirement("testing", "testtest");
		Command c4 = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.INAPPROPRIATE);
		test4.update(c4);
		Req r4 = test4.getXMLReq();
		Requirement incommingTest4 = new Requirement(r4);

		assertFalse(incommingTest4.equals(incommingTest3) || incommingTest2.equals(incommingTest1));
	}
}
