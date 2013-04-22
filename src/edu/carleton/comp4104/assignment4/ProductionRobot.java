/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;

import java.rmi.RemoteException;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;

/**
 * Production Robots take orders off of the Space 
 * and process them, determining what parts need to be created
 * to fulfill the order. For each part that needs to be created,
 * the production robot generates a request and puts that request
 * onto the java space.
 * 
 * From there, it is assumed that a robot responsible for building
 * that part will pick up the request, fulfill it, and put that part
 * onto the table. From here, the production robot takes the part
 * off the table and waits for the other parts to be completed.
 * 
 * Once a production robot has all the required parts, it builds
 * the item it is required to build and puts that item onto the table.
 * 
 * @author Andrew Thompson
 *
 */
public class ProductionRobot extends Robot{
	
	
	/**
	 * Creates a new production robot.
	 * 
	 * @author Andrew Thompson
	 */
	public ProductionRobot(){
		super();
	}
	
	/**
	 * Checks the java space for a new order, and then returns it.
	 * @return - a new order if found (null otherwise)
	 * @throws RemoteException
	 * @throws UnusableEntryException
	 * @throws TransactionException
	 * @throws InterruptedException
	 * @author Andrew Thompson
	 */
	public Order getOrder() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException{
		Order template = new Order();
		template.state = Order.NEW;
		Order order = (Order) space.take(template, null, TRANSACTION_TIME);
		return order;
	}
	
	/**
	 * Takes a new order and processes it, starting the construction process
	 * @param order - a new order
	 * @throws RemoteException
	 * @throws TransactionException
	 * @throws UnusableEntryException
	 * @throws InterruptedException
	 * @author Andrew Thompson
	 */
	public void processOrder(Order order) throws RemoteException, TransactionException, UnusableEntryException, InterruptedException{
		System.out.println(this.getClass().getSimpleName() + ": Processing order: " + order);
		order.state = Order.IN_PROCESS;
		space.write(order, null, TRANSACTION_TIME);
		Part template = PartConfigurationManager.getPart(order.partName);
		if (template == null){
			System.out.println("Part does not exist.");
			return;
		}
		template.orderName = order.customerName;
		Part part = (Part) space.take(template, null, order.deadline);
		//If the part is null, we didn't make the order in the deadline.
		if (part == null){
			System.out.println(this.getClass().getSimpleName()+": Did not finish "+ order.partName +" for " + order.customerName + " in time.");
		}
		else{
			//Else we did make it in the deadline.
			order.state = Order.COMPLETED;
			space.write(order, null, order.deadline);
		}
		
	}
	
	/**
	 * Overriden run funciton. Robot will get orders,
	 * and then process them, as long as running is true.
	 * 
	 * @author Andrew Thompson
	 */
	public void run(){
		while (running){
			try {
				Order order = getOrder();
				if (order != null){
					processOrder(order);
				}
			} catch (RemoteException | UnusableEntryException
					| TransactionException | InterruptedException e) {
				stopRunning();
				e.printStackTrace();
			}
		}
	}

}
