package edu.ncsu.csc216.tracker.model;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;

/**
 * Test most methods in RequirementList and all methods in RequirementsTrackerModel
 * @author Troy Boone
 *
 */
public class RequirementsTrackerModelTest {

	/**
	 * Test RequirementsTrackerModel and RequirementsList classes
	 */
	@Test
	public void testRequirementsTrackerModelTest() {
		
		RequirementsTrackerModel r = RequirementsTrackerModel.getInstance();
		r.addRequirement("summary", "aTestId");
		r.addRequirement("compact", "aTestId");
		r.addRequirement("concise", "aTestId");

		Object[][] guiPrintout = r.getRequirementListAsArray();
		assertEquals(0, guiPrintout[0][0]);
		assertEquals(1, guiPrintout[1][0]);
		assertEquals(2, guiPrintout[2][0]);
		assertEquals("summary", guiPrintout[0][2]);
		assertEquals("compact", guiPrintout[1][2]);
		assertEquals("concise", guiPrintout[2][2]);
		
		Requirement t = r.getRequirementById(0);
		assertEquals(0, t.getRequirementId());
		assertEquals("Submitted", t.getXMLReq().getState());
		assertEquals("summary", t.getSummary());
		Command c = new Command(CommandValue.ACCEPT, "summary", "aTestId", 2, "3 hours", null, null);
		r.executeCommand(0, c);
		
		t = r.getRequirementById(0);
		assertEquals(0, t.getRequirementId()); 
		assertEquals("Accepted", t.getXMLReq().getState());
		assertEquals("summary", t.getSummary());
		assertEquals(2, t.getPriority());
		assertEquals("3 hours", t.getEstimate());
		
		r.deleteRequirementById(0);
		t = r.getRequirementById(0);
		assertNull(t);
		
		r.createNewRequirementsList();
		t = r.getRequirementById(1);
		assertNull(t);
		t = r.getRequirementById(2);
		assertNull(t);
		
		r.loadRequirementsFromFile("test_files/req1.xml");
		assertEquals(6, r.getRequirementListAsArray().length);
		
		r.saveRequirementsToFile("test_files/actu_req1.xml");
		
		
	}
	/**
	 * Test the throws for loadRequirementsFromFile and saveRequirementsToFile
	 */
	@Test
	public void testImportandOutport() {
		RequirementsTrackerModel r = RequirementsTrackerModel.getInstance();
		try {
			r.loadRequirementsFromFile("test_files/req2.xml");
		} catch (IllegalArgumentException e) {
			
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
		try {
			r.saveRequirementsToFile("test_filesss/req2.xml");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			assertEquals("java.lang.IllegalArgumentException", e.toString());
		}
	}
	

}
