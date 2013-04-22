/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;

import java.util.StringTokenizer;

import net.jini.core.entry.Entry;

/**
 * @author Andrew Thompson
 *
 */
public class Order implements Entry{
	
	public static final String NEW = "NEW";
	public static final String IN_PROCESS = "PROCESS";
	public static final String COMPLETED = "COMPLETED";
	
	public static final String NO_PART_NAME = " ";
	
	/**
	 * @author Andrew Thompson
	 */
	private static final long serialVersionUID = -2413767107870710710L;
	public String customerName;
	public String partName;
	public Long deadline;
	public String state;
	
	
	public Order(){
	}
	
	/**
	 * Creates an order out of a single input string.
	 * Input string is parsed into proper items.
	 * @param order - input from the user
	 * @throws excepton - thrown if order can not be parsed properly
	 * @author Andrew Thompson
	 */
	public Order(String order) throws BadOrderException{
		StringTokenizer tokens = new StringTokenizer(order);
		try{
			customerName = tokens.nextToken();
			partName = tokens.nextToken();
			deadline = Long.parseLong(tokens.nextToken());
			state = NEW;
			if (customerName == null || partName == null){
				throw new BadOrderException("Bad Order:" + order);
			}
		}
		catch (Exception e){
			throw new BadOrderException("Bad Order:" + order);
		}
	}
	
	/**
	 * Creates an order out of a set of given information.
	 * @param customerName - Name of the customer the order is for.
	 * @param partName - Name of the part to be created
	 * @param deadline - Amount of milliseconds that the order has to be completed by
	 * @author Andrew Thompson
	 */
	public Order(String customerName, String partName, long deadline){
		this.customerName = customerName;
		this.partName = partName;
		this.deadline = new Long(deadline);
		this.state = IN_PROCESS;
	}
	
	/**
	 * To string function that returns a string representation of the order
	 * @return - string representation of the order
	 * @author Andrew Thompson
	 */
	public String toString(){
		return "Order by " + customerName + ", for one " + partName + " due in " + deadline;
	}
}
