package edu.ncsu.csc216.tracker.requirement;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;

/**
 * Tester for the command object
 * @author Troy Boone
 *
 */
public class CommandTest {
	

	/**
	 * Test the Command methods
	 */
	@Test
	public void testCommand() {
		Command test = new Command(CommandValue.ACCEPT , "test" , "5" , 2 , "5 hours" , "tboone" , Rejection.INAPPROPRIATE);
		assertEquals(test.getCommand() ,  CommandValue.ACCEPT);
		assertEquals(test.getSummary() ,  "test");
		assertEquals(test.getAcceptanceTestId() ,  "5");
		
		assertEquals(test.getPriority() ,  2);
		assertEquals(test.getEstimate() ,  "5 hours");
		assertEquals(test.getDeveloperId() ,  "tboone");
		assertEquals(test.getRejectionReason() ,  Rejection.INAPPROPRIATE);
		
		

	}
	/**
	 * Test IllegalArgumentException throw for different fields
	 */
	@Test
	public void testCommandInvalid() {
		try {
			Command test1 = new Command(CommandValue.ACCEPT , "test" , "5" , 2 , null , "tboone" , Rejection.INAPPROPRIATE);
			assertNull(test1);
			
		} catch (Exception e) {
	
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.ACCEPT , "test" , "5" , 2 , "" , "tboone" , Rejection.INAPPROPRIATE);
			assertNull(test1);
		} catch (Exception e) {
			
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.ACCEPT , "test" , "5" , 5 , "5 hours" , "tboone" , Rejection.INAPPROPRIATE);
			assertNull(test1);
		} catch (Exception e) {
			
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.REJECT , "test" , "5" , 0 , null , "tboone" , null);
			assertNull(test1);
		} catch (Exception e) {
			
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.ASSIGN , "test" , "5" , 2 , "5 hours" , "" , Rejection.INAPPROPRIATE);
			assertNull(test1);
		} catch (Exception e) {
		
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.ASSIGN , "test" , "5" , 2 , "5 hours" , null , Rejection.INAPPROPRIATE);
			assertNull(test1);
		} catch (Exception e) {
		
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.REVISE , "" , "5" , 2 , "5 hours" , "tboone" , Rejection.INAPPROPRIATE);
			assertNull(test1);
		} catch (Exception e) {
	
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.REVISE , null , "5" , 2 , "5 hours" , "tboone" , Rejection.INAPPROPRIATE);
			assertNull(test1);
		} catch (Exception e) {
	
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.REVISE , "test" , "" , 2 , "5 hours" , "tboone" , Rejection.INAPPROPRIATE);
			assertNull(test1);
		} catch (Exception e) {
	
			e.getStackTrace();
		}
		try {
			Command test1 = new Command(CommandValue.REVISE , "test" , null , 2 , "5 hours" , "tboone" , Rejection.INAPPROPRIATE);
			assertNull(test1);
		} catch (Exception e) {

			e.getStackTrace();
		}
		try {
			Command test1 = new Command(null , null , null , 0 , null , null , null);
			assertNull(test1);
		} catch (Exception e) {

			e.getStackTrace();
		}
	}
	}
