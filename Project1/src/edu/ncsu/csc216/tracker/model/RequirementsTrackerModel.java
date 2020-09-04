package edu.ncsu.csc216.tracker.model;

import java.util.ArrayList;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.xml.RequirementIOException;
import edu.ncsu.csc216.tracker.xml.RequirementsReader;
import edu.ncsu.csc216.tracker.xml.RequirementsWriter;

/**
 * Second class used to manage the collection of Requirements. This method calls
 * upon RequirementsList for its get, add, remove, and execute methods, while
 * calling RequirementsWriter and RequirementsReader for output and input.
 * 
 * @author Troy Boone
 *
 */
public class RequirementsTrackerModel {
	/** RequirementsTrackerModel singleton instance */
	private static RequirementsTrackerModel singleton;
	/** RequirementsList object */
	private RequirementsList reqList;

	/**
	 * Constructor for the RequirementsTrackerModel object. It will call the
	 * RequirementsList to generate one list to maintain. It is made private so
	 * there will only be 1 instance of this object
	 */
	private RequirementsTrackerModel() {
		reqList = new RequirementsList();
	}

	/**
	 * Getter for RequirementsTrackerModel singleton. If there has not been one
	 * made, this method will initialize it
	 * 
	 * @return singleton
	 */
	public static RequirementsTrackerModel getInstance() {
		if (singleton == null) {
			singleton = new RequirementsTrackerModel();
		}
		return singleton;

	}

	/**
	 * Calls RequirementsWriter to handle writing to a file. This method then will
	 * iterate over the reqList and add each of them to the writter.
	 * 
	 * @param filename the location of the output file
	 */
	public void saveRequirementsToFile(String filename) {
		try {
			RequirementsWriter write = new RequirementsWriter(filename);
			for (int i = 0; i < reqList.getRequirements().size(); i++) {
				write.addReq(reqList.getRequirements().get(i).getXMLReq());
			}
			write.marshal();
		} catch (RequirementIOException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Calls RequirementsReader to handle reading from a file. This method will then
	 * take the list of requirements sent from RequirementsReader and add it to a
	 * fresh RequirementsList
	 * 
	 * @param filename the location of the input file
	 * @throws IllegalArgumentException if the file cannot be found
	 */
	public void loadRequirementsFromFile(String filename) {
		try {
			RequirementsReader read = new RequirementsReader(filename);
			createNewRequirementsList();
			reqList.addXMLReqs(read.getReqs());
		} catch (RequirementIOException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Simple reset for reqList
	 */
	public void createNewRequirementsList() {
		reqList = new RequirementsList();
		Requirement.setCounter(0);
	}

	/**
	 * Makes a new 2d object array called catalog which is then filled with
	 * requirements information in the format of requirement id - state - summary.
	 * This is then returned
	 * 
	 * @return catalog the GUI displayable array
	 */
	public Object[][] getRequirementListAsArray() {
		ArrayList<Requirement> requirementList = (ArrayList<Requirement>) reqList.getRequirements();
		Object[][] catalog = new Object[requirementList.size()][3];

		for (int i = 0; i < requirementList.size(); i++) {
			catalog[i][0] = requirementList.get(i).getRequirementId();
			catalog[i][1] = requirementList.get(i).getXMLReq().getState();
			catalog[i][2] = requirementList.get(i).getSummary();
		}
		return catalog;

	}

	/**
	 * Getter for requirement by using the reqId. Calls upon RequirementsList method
	 * 
	 * @param reqId the id of the requirement that is being found
	 * @return the requirement with matching Id
	 */
	public Requirement getRequirementById(int reqId) {
		return reqList.getRequirementById(reqId);

	}

	/**
	 * Executes the command c by calling RequirementsList executeCommand on the
	 * requirement with matching requirement id reqId
	 * 
	 * @param reqId the id of the requirement that the command is being executed on
	 * @param c     the command the requirement is executing
	 */
	public void executeCommand(int reqId, Command c) {
		reqList.executeCommand(reqId, c);
	}

	/**
	 * Deletes the requirement with matching requirement id reqId by calling
	 * RequirementsList deleteRequirementById
	 * 
	 * @param reqId the id of the requirement that is being deleted
	 */
	public void deleteRequirementById(int reqId) {
		reqList.deleteRequirementById(reqId);
	}

	/**
	 * Adds the requirement by calling RequirementsList executeCommand on the
	 * requirement with matching requirement id reqId
	 * 
	 * @param summary          the requirement summary
	 * @param acceptanceTestId the requirement acceptanceTestId
	 */
	public void addRequirement(String summary, String acceptanceTestId) {
		reqList.addRequirement(summary, acceptanceTestId);
	}
}
