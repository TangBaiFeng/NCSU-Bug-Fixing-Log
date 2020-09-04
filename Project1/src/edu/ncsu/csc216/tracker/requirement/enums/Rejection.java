package edu.ncsu.csc216.tracker.requirement.enums;

/**
 * List of acceptable rejection reasons for requirement and command to use
 * 
 * @author Troy Boone
 *
 */
public enum Rejection {
	/** Duplicate requirement */
	DUPLICATE,
	/** Infeasible to implement requirement */
	INFEASIBLE,
	/** Too large of a requirement */
	TOO_LARGE,
	/** Out of scope of project requirement */
	OUT_OF_SCOPE,
	/** Inappropriate requirement */
	INAPPROPRIATE
}
