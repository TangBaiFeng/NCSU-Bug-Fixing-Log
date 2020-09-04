package edu.ncsu.csc216.tracker.requirement;

import edu.ncsu.csc216.tracker.requirement.enums.Rejection;
import edu.ncsu.csc216.tracker.xml.Req;

/**
 * Context and object class for the Requirement class. This class contains the
 * finite state machine that command uses to change from state to state.
 * Requirement handles the getter and setters for almost all fields
 * 
 * @author Troy Boone
 *
 */

public class Requirement {

	/** The incrementing test ID */
	private int requirementId;
	/** The summary of requirement */
	private String summary;
	/** The acceptance test ID */
	private String acceptanceTestId;
	/** The priority to completion */
	private int priority;
	/** The time estimate to implement the requirement */
	private String estimate;
	/** The developer assigned to the current state of the requirement */
	private String developer;
	/** name of Submitted state*/
	public static final String SUBMITTED_NAME = "Submitted";
	/** name of Accepted state */
	public static final String ACCEPTED_NAME = "Accepted";
	/** name of Rejected state*/
	public static final String REJECTED_NAME = "Rejected";
	/** name of Working state*/
	public static final String WORKING_NAME = "Working";
	/** name of Completed state*/
	public static final String COMPLETED_NAME = "Completed";
	/** name of Verified state*/
	public static final String VERIFIED_NAME = "Verified";
	
	/** name of Duplicate rejection reason*/
	public static final String DUPLICATE_NAME = "Duplicate";
	/** name of Infeasible rejection reason*/
	public static final String INFEASIBLE_NAME = "Infeasible";
	/** name of Too large rejection reason*/
	public static final String TOO_LARGE_NAME = "Too large";
	/** name of Out of Scope rejection reason*/
	public static final String OUT_OF_SCOPE_NAME = "Out of Scope";
	/** name of Inappropriate rejection reason*/
	public static final String INAPPROPRIATE_NAME = "Inappropriate";
	
	/** The static counter to assign to the requirement id */
	private static int counter = 0;
	/** XML representation of this object*/
	private Req req;
	/** reason for rejection*/
	private Rejection rejectionReason;
	/** initializing submittedState for finite state machine*/
	private final RequirementState submittedState = new SubmittedState();
	/** initializing acceptedState for finite state machine*/
	private final RequirementState acceptedState = new AcceptedState();
	/** initializing rejectedState for finite state machine*/
	private final RequirementState rejectedState = new RejectedState();
	/** initializing workingState for finite state machine*/
	private final RequirementState workingState = new WorkingState();
	/** initializing completedState for finite state machine*/
	private final RequirementState completedState = new CompletedState();
	/** initializing verifiedState for finite state machine*/
	private final RequirementState verifiedState = new VerifiedState();
	
	/** The current state for the requirement */
	private RequirementState state = submittedState;

	
	/**
	 * Creates a new Requirement from a summary and acceptanceTestId. The ID is
	 * assigned to be the next number after the highest existing requirementId
	 * 
	 * @param summary the requirements summary
	 * @param acceptanceTestId the requirements acceptance test id
	 * 
	 */
	public Requirement(String summary, String acceptanceTestId) {
		this.summary = summary;
		setAcceptanceTestId(acceptanceTestId);
		requirementId = counter;
		incrementCounter();
	}

	/**
	 * Creates a new Requirement from a Req object from RequirementTrackerXML. This
	 * will call each of the methods with the information got using the getters from
	 * the Req class and object
	 * 
	 * @param incoming requirement being added
	 */
	public Requirement(Req incoming) {
		summary = incoming.getSummary();
		requirementId = incoming.getId();
		estimate = incoming.getEstimate();
		priority = incoming.getPriority();
		setState(incoming.getState());
		setDeveloper(incoming.getDeveloper());
		setAcceptanceTestId(incoming.getTest());
		setRejectionReasonString(incoming.getRejection());

	}

	/**
	 * Static method to increment the counter on a requirement being added
	 */
	private static void incrementCounter() {
		counter++;
	}

	/**
	 * Simple getter for requirementId
	 * 
	 * @return the requirementId
	 */
	public int getRequirementId() {
		return requirementId;
	}

	/**
	 * Simple getter for requirementState
	 * 
	 * @return the requirementState
	 */
	public RequirementState getState() {
		return state;
	}

	
	/**
	 * Setter for setState. If the string matches any of the declared String, it
	 * sets this objects state to that state
	 * 
	 * @throws IllegalArgumentException if state is not one of the accepted objects
	 * @param state the string representation of state
	 */
	private void setState(String state) {
		switch (state) {
		case SUBMITTED_NAME:
			this.state = submittedState;
			break;
		case ACCEPTED_NAME:
			this.state = acceptedState;
			break;
		case REJECTED_NAME:
			this.state = rejectedState;
			break;
		case WORKING_NAME:
			this.state = workingState;
			break;
		case COMPLETED_NAME:
			this.state = completedState;
			break;
		case VERIFIED_NAME:
			this.state = verifiedState;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Simple getter for priority
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Simple getter for estimate
	 * 
	 * @return the estimate
	 */
	public String getEstimate() {
		return estimate;
	}

	/**
	 * Simple getter for rejectionReason
	 * 
	 * @return rejectionReason
	 */
	public Rejection getRejectionReason() {
		return rejectionReason;

	}

	/**
	 * Getter for rejectionReason as a string. It will call the string name
	 * associated to rejectionReason
	 * 
	 * @return the rejectionReason string or null
	 */
	public String getRejectionReasonString() {
		if (rejectionReason == null) {
			return null;
		}
		switch (rejectionReason) {
		case DUPLICATE:
			return DUPLICATE_NAME;
		case INAPPROPRIATE:
			return INAPPROPRIATE_NAME;
		case INFEASIBLE:
			return INFEASIBLE_NAME;
		case OUT_OF_SCOPE:
			return OUT_OF_SCOPE_NAME;
		case TOO_LARGE:
			return TOO_LARGE_NAME;
			default: 
				return null;
		}

	}

	/**
	 * Setter for the rejectionReason. It will call the rejection enum associated
	 * with the rejectionString
	 * 
	 * @param rejectionReason enum
	 * @return null if rejectionString is null
	 */
	private void setRejectionReasonString(String rejectionString) {
		if (rejectionString == null) {
			return;
		}
		switch (rejectionString) {
		case DUPLICATE_NAME:
			rejectionReason = Rejection.DUPLICATE;
			break;
		case INFEASIBLE_NAME:
			rejectionReason = Rejection.INFEASIBLE;
			break;
		case TOO_LARGE_NAME:
			rejectionReason = Rejection.TOO_LARGE;
			break;
		case OUT_OF_SCOPE_NAME:
			rejectionReason = Rejection.OUT_OF_SCOPE;
			break;
		case INAPPROPRIATE_NAME:
			rejectionReason = Rejection.INAPPROPRIATE;
			break;
		default: 
			return;
		}
		
	}

	/**
	 * Simple getter for developer
	 * 
	 * @return the developerId
	 */
	public String getDeveloper() {
		return developer;
	}

	/**
	 * Simple getter for summary
	 * 
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Simple getter for acceptanceTestId
	 * 
	 * @return the acceptanceTestId
	 */
	public String getAcceptanceTestId() {
		return acceptanceTestId;
	}

	/**
	 * Simple setter for acceptanceTestId
	 * 
	 * @param acceptanceTestId the acceptanceTestId to set
	 */
	public void setAcceptanceTestId(String acceptanceTestId) {
		this.acceptanceTestId = acceptanceTestId;
	}

	/**
	 * Simple setter for developerId
	 * 
	 * @param developerId the developerId to set
	 */
	public void setDeveloper(String developerId) {
		this.developer = developerId;
	}

	/**
	 * Update the Requirement by using the command c to change what state which will
	 * set variables to new values or set them as null
	 * 
	 * @param c the command changing the Requirement
	 */
	public void update(Command c) {
		state.updateState(c);
	}

	/**
	 * Creates a new Req using the information from this Requirement. Calls the
	 * setters from Req to use their context.
	 * 
	 * @return req
	 */
	public Req getXMLReq() {
		req = new Req();
		if (getState().equals(submittedState)) {
			req.setState(SUBMITTED_NAME);
		}
		if (getState().equals(acceptedState)) {
			req.setState(ACCEPTED_NAME);
		}
		if (getState().equals(rejectedState)) {
			req.setState(REJECTED_NAME);
		}
		if (getState().equals(workingState)) {
			req.setState(WORKING_NAME);
		}
		if (getState().equals(completedState)) {
			req.setState(COMPLETED_NAME);
		}
		if (getState().equals(verifiedState)) {
			req.setState(VERIFIED_NAME);
		}

		req.setTest(acceptanceTestId);
		req.setSummary(summary);
		req.setId(requirementId);
		req.setDeveloper(developer);
		req.setEstimate(estimate);
		req.setRejection(getRejectionReasonString());
		req.setPriority(priority);
		return req;

	}

	/**
	 * Setter for the counter. This will cause the next requirement added to have
	 * the new counter as its requirementId
	 * 
	 * @param count the new count
	 */
	public static void setCounter(int count) {
		if (count >= 0) {
			Requirement.counter = count;
		}
	}

	/**
	 * First state in finite state machine. Requirement starts at this state, and
	 * can transition to Accepted or Rejected
	 * 
	 * @author Troy Boone
	 *
	 */
	class SubmittedState implements RequirementState {

		private SubmittedState() {

		}
		/**
		 * The update state can move to accepted with Command.ACCEPT and setting
		 * priority and estimate or Command.REJECT and set estimate, priority and developer to null,
		 * while giving a rejection reason
		 * 
		 * @param c the command changing the state
		 * @throws UnsupportedOperationException if Command is not ACCEPT or REJECT
		 */
		@Override
		public void updateState(Command c) {
			switch (c.getCommand()) {
			case ACCEPT:
				priority = c.getPriority();
				estimate = c.getEstimate();
				state = acceptedState;
				break;

			case REJECT:
				estimate = null;
				priority = 0;
				developer = null;
				rejectionReason = c.getRejectionReason();
				state = rejectedState;
				break;
			default:
				throw new UnsupportedOperationException();
			}

		}

		/**
		 *Simple getter for SUBMITTED_NAME
		 *@return SUBMITTED_NAME
		 */
		@Override
		public String getStateName() {

			return SUBMITTED_NAME;
		}

	}

	/**
	 * Second state in finite state machine. Submitted state can transition to this
	 * one, and this one can transition to Working or Rejected
	 * 
	 * @author Troy Boone
	 *
	 */
	class AcceptedState implements RequirementState {

		private AcceptedState() {

		}

		/**
		 * The update state can move to Working with Command.ASSIGN and setting
		 * developer or Command.REJECT and set estimate, priority and developer to null,
		 * while giving a rejection reason
		 * 
		 * @param c the command changing the state
		 * @throws UnsupportedOperationException if Command is not ASSIGN or REJECT
		 */
		@Override
		public void updateState(Command c) {
			switch (c.getCommand()) {
			case ASSIGN:
				state = workingState;
				developer = c.getDeveloperId();
				break;

			case REJECT:
				estimate = null;
				priority = 0;
				developer = null;
				rejectionReason = c.getRejectionReason();
				state = rejectedState;
				break;
			default:
				throw new UnsupportedOperationException();
			}

		}

		/**
		 *Simple getter for ACCEPTED_NAME
		 *@return ACCEPTED_NAME
		 */
		@Override
		public String getStateName() {
			return ACCEPTED_NAME;
		}

	}

	/**
	 * The failure state for the finite state machine. All states can transition
	 * here with Command.REJECT and set estimate, priority and developer to null,
	 * while giving a rejection reason. It can transition to Submitted state
	 * 
	 * @author Troy Boone
	 *
	 */
	class RejectedState implements RequirementState {

		private RejectedState() {

		}

		/**
		 * The update state can only move to submitted with Command.REVISE and setting
		 * summary and acceptanceTestId again, while keeping the original rejection
		 * reason until changed
		 * 
		 * @param c the command changing the state
		 * @throws UnsupportedOperationException if Command is not REVISE
		 */
		@Override
		public void updateState(Command c) {
			switch (c.getCommand()) {

			case REVISE:
				summary = c.getSummary();
				acceptanceTestId = c.getAcceptanceTestId();
				state = submittedState;
				rejectionReason = c.getRejectionReason();
				break;
			default:
				throw new UnsupportedOperationException();
			}

		}

		/**
		 *Simple getter for REJECTED_NAME
		 * @return REJECTED_NAME
		 */
		@Override
		public String getStateName() {

			return REJECTED_NAME;
		}
	}

	/**
	 * Third state in finite state machine. Accepted and Completed state can
	 * transition to this one, and this one can transition to Completed or Rejected
	 * 
	 * @author Troy Boone
	 *
	 */
	class WorkingState implements RequirementState {

		private WorkingState() {

		}

		/**
		 * The update state can either move to completed with Command.COMPLETE or
		 * Command.REJECT and set estimate, priority and developer to null, while giving
		 * a rejection reason
		 * 
		 * @param c the command changing the state
		 * @throws UnsupportedOperationException if Command is not COMPLETE or REJECT
		 */
		@Override
		public void updateState(Command c) {
			switch (c.getCommand()) {
			case COMPLETE:
				state = completedState;
				break;

			case REJECT:
				estimate = null;
				priority = 0;
				developer = null;
				rejectionReason = c.getRejectionReason();
				state = rejectedState;
				break;
			default:
				throw new UnsupportedOperationException();
			}

		}

		/**
		 * Simple getter for WORKING_NAME
		 * @return WORKING_NAME
		 */
		@Override
		public String getStateName() {

			return WORKING_NAME;
		}

	}

	/**
	 * Fourth state in finite state machine. Working state can transition to this
	 * one, and this one can transition to Working, Verified, or Rejected
	 * 
	 * @author Troy Boone
	 *
	 */
	class CompletedState implements RequirementState {

		private CompletedState() {

		}

		/**
		 * The update state can either move to working with Command.ASSIGN and set
		 * developer, Command.PASS to move to verified state, Command.FAIL to move to
		 * working state or Command.REJECT and set estimate, priority and developer to
		 * null, while giving a rejection reason
		 * 
		 * @param c the command changing the state
		 * @throws UnsupportedOperationException if Command is not ASSIGN, PASS, FAIL,
		 *                                       or REJECT
		 */
		@Override
		public void updateState(Command c) {
			switch (c.getCommand()) {
			case FAIL:
				state = workingState;
				break;

			case PASS:
				state = verifiedState;
				break;

			case ASSIGN:
				state = workingState;
				developer = c.getDeveloperId();
				break;

			case REJECT:
				estimate = null;
				priority = 0;
				developer = null;
				rejectionReason = c.getRejectionReason();
				state = rejectedState;
				break;
			default:
				throw new UnsupportedOperationException();
			}

		}

		/**
		 *Simple getter for COMPLETED_NAME
		 *@return COMPLETED_NAME
		 */
		@Override
		public String getStateName() {

			return COMPLETED_NAME;
		}

	}

	/**
	 * Fifth state in finite state machine. Completed state can transition to this
	 * one, and this one can transition to Working or Rejected
	 * 
	 * @author Troy Boone
	 *
	 */
	class VerifiedState implements RequirementState {

		private VerifiedState() {

		}

		/**
		 * The update state can either move to working with Command.ASSIGN and set
		 * developer or reject and set estimate, priority and developer to null, while
		 * giving a rejection reason
		 * 
		 * @param c the command changing the state
		 * @throws UnsupportedOperationException if Command is not ASSIGN or REJECT
		 */
		@Override
		public void updateState(Command c) {
			switch (c.getCommand()) {
			case ASSIGN:
				state = workingState;
				developer = c.getDeveloperId();
				break;

			case REJECT:
				estimate = null;
				priority = 0;
				developer = null;
				rejectionReason = c.getRejectionReason();
				state = rejectedState;
				break;
			default:
				throw new UnsupportedOperationException();
			}

		}

		/**
		 * Simple getter for VERIFIED_NAME
		 * @return VERIFIED_NAME
		 */
		@Override
		public String getStateName() {
			return VERIFIED_NAME;
		}

	}
}
