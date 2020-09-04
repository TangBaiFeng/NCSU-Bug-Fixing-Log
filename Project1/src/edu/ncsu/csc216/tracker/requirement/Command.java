package edu.ncsu.csc216.tracker.requirement;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;

/**
 * Objected used by the GUI to help the requirement object transition through
 * states using the command enums. By encapsulating the requirement object, the
 * enum can be used for permisisons through different states
 * 
 * @author Troy Boone
 *
 */

public class Command {
	/** The summary to change to */
	private String summary;
	/** The acceptanceTestId to change to with permission */
	private String acceptanceTestId;
	/** The developerId to change to with permission */
	private String developerId;
	/** The time estimate to change to with permission */
	private String estimate;
	/** The test priority to change to with permission */
	private int priority;
	/**
	 * The command enum used to determine which action the requirement should take
	 * at each state
	 */
	private CommandValue c;
	/** The rejection enum used to indicate why a requirement was rejected */
	private Rejection resolutionReason;

	/**
	 * Constructs a Command object that will encapsulate a Requirement. This command
	 * will be used by the finite state machine to determine what values to change
	 * at what state the requirement is currently at.
	 * 
	 * @param designation      The command enum
	 * @param summary          the requirements summary
	 * @param acceptanceTestId the requirements acceptanceTestId
	 * @param priority         the requirements priority
	 * @param estimate         the requirements estimate
	 * @param developerId      the requirements developerId
	 * @param resolutionReason the requirements resolutionReason
	 * 
	 * @throws IllegalArgumentException if during the ACCEPT command, estimate is a
	 *                                  null or empty string and priority is not
	 *                                  between 1 and 3
	 * @throws IllegalArgumentException if during the REJECT command and the
	 *                                  resolutionReason is null
	 * @throws IllegalArgumentException if during the ASSIGN command and the
	 *                                  developerId is either empty or null
	 * @throws IllegalArgumentException if during the REVISE command, either the
	 *                                  summary or acceptanceTestId are a null or
	 *                                  empty string
	 * @throws IllegalArgumentException if designation is null
	 */
	public Command(CommandValue designation, String summary, String acceptanceTestId, int priority, String estimate,
			String developerId, Rejection resolutionReason) {

		if (designation == CommandValue.ACCEPT
				&& (estimate == null || estimate.equals("") || priority > 3 || priority < 1)) {

			throw new IllegalArgumentException();

		}
		if (designation == CommandValue.REJECT && resolutionReason == null) {
			throw new IllegalArgumentException();
		}
		if (designation == CommandValue.ASSIGN && (developerId == null || developerId.equals(""))) {

			throw new IllegalArgumentException();

		}
		if (designation == CommandValue.REVISE
				&& (summary == null || acceptanceTestId == null || summary.equals("") || acceptanceTestId.equals(""))) {

			throw new IllegalArgumentException();

		}
		if (designation == null) {
			throw new IllegalArgumentException();

		}

		this.summary = summary;
		this.acceptanceTestId = acceptanceTestId;
		this.priority = priority;
		this.estimate = estimate;
		this.developerId = developerId;
		this.c = designation;
		this.resolutionReason = resolutionReason;
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
	 * Simple getter for command enum
	 * 
	 * @return c
	 */
	public CommandValue getCommand() {
		return c;
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
	 * Simple getter for priority
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Simple getter for developerId
	 * 
	 * @return the developerId
	 */
	public String getDeveloperId() {
		return developerId;
	}

	/**
	 * Simple getter for rejectionReason
	 * 
	 * @return resolutionReason
	 */
	public Rejection getRejectionReason() {
		return resolutionReason;
	}

}
