package edu.ncsu.csc216.tracker.model;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.xml.Req;

/**
 * One of the classes responsible for managing the list of requirements. Has the
 * add, get, and remove methods for requirements, in addition to being able to
 * execute commands on a specific requirement in the list
 * 
 * @author Troy Boone
 *
 */
public class RequirementsList {
	/** The list of requirements */
	private ArrayList<Requirement> requirements;

	/**
	 * Constructs a list of requirements. The counter for this list is set initially
	 * at 0
	 */
	public RequirementsList() {
		Requirement.setCounter(0);
		requirements = new ArrayList<Requirement>();
		
	}

	/**
	 * Simple add method. Adds NEW requirements to the requirement list.
	 * 
	 * @param summary          the requirements summary
	 * @param acceptanceTestId the requirements acceptanceTestId
	 * @return the index of the added object
	 */
	public int addRequirement(String summary, String acceptanceTestId) {
		Requirement r = new Requirement(summary, acceptanceTestId);
		requirements.add(r);
		return requirements.size() - 1;
	}

	/**
	 * Add method for existing requirements. Will add requirements from an list of
	 * XML and will set the counter to 1 more then the highest requirement added
	 * 
	 * @param readerInport the list of Req to be added
	 */
	public void addXMLReqs(List<Req> readerInport) {
		for (int i = 0; i < readerInport.size(); i++) {
			Requirement r = new Requirement(readerInport.get(i));
			this.requirements.add(r);
		}
		Requirement.setCounter(requirements.get(requirements.size() - 1).getRequirementId() + 1);
	}

	/**
	 * Simple getter for requirements
	 * 
	 * @return requirements list
	 */
	public List<Requirement> getRequirements() {
		return requirements;

	}

	/**
	 * Iterates through requirements list and if a requirement has the same ID as
	 * the input, it is returned
	 * 
	 * @param reqId the reqId being looked for
	 * @return the requirement in the list with matching reqId, null otherwise
	 */
	public Requirement getRequirementById(int reqId) {
		for (int i = 0; i < requirements.size(); i++) {
			if (requirements.get(i).getRequirementId() == reqId) {
				return requirements.get(i);
			}
		}
		return null;

	}

	/**
	 * Calls the getRequirementById method to find the requirement with matching ID,
	 * then using the update method to execute a command on the requirement
	 * 
	 * @param reqId the id of the requirement that the command is being executed on
	 * @param c     the command being executed
	 */
	public void executeCommand(int reqId, Command c) {

		if (getRequirementById(reqId) != null) {
			getRequirementById(reqId).update(c);
		}
	}

	/**
	 * Calls the getRequirementById method to find the requirement with matching ID,
	 * then calls ArrayList remove method to remove it from the list
	 * 
	 * @param reqId the id of the requirement being removed
	 * 
	 */
	public void deleteRequirementById(int reqId) {

		if (getRequirementById(reqId) != null) {
			requirements.remove(getRequirementById(reqId));
		}
	}

}
