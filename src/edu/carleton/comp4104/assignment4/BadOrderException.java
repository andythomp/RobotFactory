/**
 * 
 */
package edu.carleton.comp4104.assignment4;

/**
 * This exception is passed back when a user gives input for an order,
 * and that input is unparseable or bad.
 * @author Andrew Thompson
 *
 */
public class BadOrderException extends Exception {

	/**
	 * @author Andrew Thompson
	 */
	private static final long serialVersionUID = 2324055622009557464L;

	/**
	 * Creates a new BadOrderException with a given message.
	 * @param string - Bad Order message.
	 * @author Andrew Thompson
	 */
	public BadOrderException(String string) {
		super(string);
	}


}
