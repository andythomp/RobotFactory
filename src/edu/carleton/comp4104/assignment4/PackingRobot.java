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
 *
 * This robot simply loks for completed orders and states that they are completed.
 * @author Andrew Thompson
 *
 */
public class PackingRobot extends Robot{

	
	/**
	 * Looks for Orders with a type of completed.
	 * If it finds an order, it removes it from the space and says that the order is completed.
	 * @throws RemoteException
	 * @throws UnusableEntryException
	 * @throws TransactionException
	 * @throws InterruptedException
	 * @author Andrew Thompson
	 */
	public void completeOrder() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException{
		Order template = new Order();
		template.state = Order.COMPLETED;
		Order order = (Order) space.take(template, null, TRANSACTION_TIME);
		if (order != null){
			System.out.println(this.getClass().getSimpleName() + ": COMPLETED:[" + order +"]");
		}
	}
	
	/**
	 * Causes the robot to continuously look for completed
	 * orders as long as running is true.
	 * @author Andrew Thompson
	 */
	@Override
	public void run() {
		while (running){
			try {
				completeOrder();
			} catch (RemoteException | UnusableEntryException
					| TransactionException | InterruptedException e) {
				stopRunning();
				e.printStackTrace();
			}
		}
	}

}
