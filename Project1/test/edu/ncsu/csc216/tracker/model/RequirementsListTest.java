package edu.ncsu.csc216.tracker.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;
import edu.ncsu.csc216.tracker.xml.Req;

/**
 * Test the RequirementsList class. All methods are being used by
 * RequirementsTrackerModel, so this junit testing5 set are specific cases that
 * needed to be checked.
 * 
 * @author Troy Boone
 *
 */
public class RequirementsListTest {

	/**
	 * Test constructing a RequirementsList object
	 */
	@Test
	public void testing5RequirementsList() {
		RequirementsList test = new RequirementsList();
		Requirement.setCounter(0);
		assertEquals(0, test.getRequirements().size());

		int location1 = test.addRequirement("summary", "testing5id");
		int location2 = test.addRequirement("summary2", "testing5id2");
		assertNotNull(test.getRequirementById(0));
		assertEquals(0, location1);
		assertEquals(1, location2);
		assertEquals(0, test.getRequirementById(0).getRequirementId());
		assertEquals(1, test.getRequirementById(1).getRequirementId());
		assertEquals("summary", test.getRequirementById(0).getSummary());
		assertEquals("summary2", test.getRequirementById(1).getSummary());
		
		Requirement r = new Requirement("req1", null);
		Requirement f = new Requirement("req2", null);
		
		ArrayList<Req> reqList = new ArrayList<Req>();
		reqList.add(r.getXMLReq());
		reqList.add(f.getXMLReq());
		
		test.addXMLReqs(reqList);
		assertEquals(2, test.getRequirementById(2).getRequirementId());
		assertEquals(3, test.getRequirementById(3).getRequirementId());
		assertEquals("req1", test.getRequirementById(2).getSummary());
		assertEquals("req2", test.getRequirementById(3).getSummary());
		
		test.deleteRequirementById(3);
		assertNull(test.getRequirementById(3));

		Command c = new Command(CommandValue.ACCEPT, null, null, 1, "1 day", null, null);
		test.executeCommand(0, c);
		assertEquals("Accepted", test.getRequirementById(0).getXMLReq().getState());
		assertEquals(1, test.getRequirementById(0).getPriority());
		assertEquals("1 day", test.getRequirementById(0).getEstimate());

		c = new Command(CommandValue.ASSIGN, null, null, 0, null, "dev", null);
		test.executeCommand(0, c);
		assertEquals("Working", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test.executeCommand(0, c);
		assertEquals("Completed", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.PASS, null, null, 0, null, null, null);
		test.executeCommand(0, c);
		assertEquals("Verified", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.ASSIGN, null, null, 0, null, "dev2", null);
		test.executeCommand(0, c);
		assertEquals("Working", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test.executeCommand(0, c);
		assertEquals("Completed", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.FAIL, null, null, 0, null, null, null);
		test.executeCommand(0, c);
		assertEquals("Working", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test.executeCommand(0, c);
		assertEquals("Completed", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.ASSIGN, null, null, 0, null, "dev", null);
		test.executeCommand(0, c);
		assertEquals("Working", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		test.executeCommand(0, c);
		assertEquals("Completed", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.PASS, null, null, 0, null, null, null);
		test.executeCommand(0, c);
		assertEquals("Verified", test.getRequirementById(0).getXMLReq().getState());

		c = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.OUT_OF_SCOPE);
		test.executeCommand(0, c);
		assertEquals("Rejected", test.getRequirementById(0).getXMLReq().getState());
		
		

	}

}
