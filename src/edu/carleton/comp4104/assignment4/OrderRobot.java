/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import net.jini.core.transaction.TransactionException;

/**
 * OrderRobots take input from the user, generate orders out
 * of that input, and put those orders onto the Space. 
 * 
 * @author Andrew Thompson
 *
 */
public class OrderRobot extends Robot{

	public ArrayList<Order> orders;
	public BufferedReader br;
	
	/**
	 * Creates a new order robot
	 * @author Andrew Thompson
	 */
	public OrderRobot(){
		super();
		orders = new ArrayList<Order>();
	}
	
	/**
	 * Takes orders from the System.in, parse the order, and put that
	 * order onto the java space.
	 * @throws IOException
	 * @throws TransactionException
	 * @author Andrew Thompson
	 */
	public void takeOrders() throws IOException, TransactionException{
		 System.out.println(this.getClass().getSimpleName() + ": Please enter your order [should be name part_name deadline]:");
		 String input = br.readLine();
		 try {
			Order order = new Order(input);
			space.write(order, null, order.deadline);
		} catch (BadOrderException e) {
			System.out.println("That is not a valid order [should be name part_name deadline]");
		}
	}

	/**
	 * Overriden run function, causes the order robot
	 * to continuously take orders while running remains true.
	 * @author Andrew Thompson
	 */
	public void run(){
		br = new BufferedReader(new InputStreamReader(System.in));
		while(running){
			try {
				takeOrders();
			} catch (IOException | TransactionException e) {
				stopRunning();
				e.printStackTrace();
			} 
		}
	}
}
