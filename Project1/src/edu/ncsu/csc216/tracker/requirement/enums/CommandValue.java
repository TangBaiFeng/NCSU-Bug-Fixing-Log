package edu.ncsu.csc216.tracker.requirement.enums;

/**
 * List of acceptable commands for requirement to use to transition states
 * 
 * @author Troy Boone
 *
 */
public enum CommandValue {
	/** Accepted requirement: Submitted -> Accepted */
	ACCEPT,
	/** Rejected requirement: Any State -> Rejected */
	REJECT,
	/** Revised requirement: Rejected -> Submitted */
	REVISE,
	/** Assigned requirement: Accepted -> Working, or Completed -> Working */
	ASSIGN,
	/** Completed requirement: Working -> Completed */
	COMPLETE,
	/** Passed requirement: Completed -> Verified */
	PASS,
	/** Failed requirement: Completed -> Working */
	FAIL
}
