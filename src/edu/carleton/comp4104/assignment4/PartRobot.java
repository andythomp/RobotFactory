/**
 * 
 */
package edu.carleton.comp4104.assignment4;

import java.rmi.RemoteException;
import java.util.ArrayList;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;

/**
 * Part Robots are responsible for building parts. They take in
 * a configuration, a lifespan, and a name.
 * 
 * Once set to work, they build the part specified in their 
 * configuration.
 * 
 * @author Andrew Thompson
 *
 */
public class PartRobot extends Robot{


	/**
	 * Creates a new part robot. should be used cautiously, as the robot will have 
	 * a default setup and will not have any known configuration.
	 * @author Andrew Thompson
	 */
	public PartRobot(){
		super();
	}
	/**
	 * Creates a new robot out of given parameters
	 * @param robotName - Name of the robot (used for System.out)
	 * @param lifespan - Lifespan of the robot (robot dies after lifespan runs out in MS)
	 * @param config - Name of the part the robot is going to build
	 * @author Andrew Thompson
	 */
	public PartRobot(String robotName, long lifespan, String config){
		super(robotName, lifespan, config);
	}

	/**
	 * Gets an order in process from the java space
	 * @return - new order, or null if none found
	 * @throws RemoteException
	 * @throws UnusableEntryException
	 * @throws TransactionException
	 * @throws InterruptedException
	 * @author Andrew Thompson
	 */
	public Order getOrder() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException{
		Order template = new Order();
		template.partName = config;
		template.state = Order.IN_PROCESS;
		Order order = (Order) space.take(template, null, TRANSACTION_TIME);
		return order;
	}
	
	/**
	 * Processes a given order. For each requirement the ordered part has, this
	 * will put a new order onto the java space for that part. Once this robot
	 * receives all the parts it has ordered, it makes it's own part and puts it onto the
	 * java space.
	 * @param order - Order to be processed
	 * @throws InterruptedException
	 * @throws RemoteException
	 * @throws TransactionException
	 * @throws BadOrderException
	 * @throws UnusableEntryException
	 * @author Andrew Thompson
	 */
	public void processOrder(Order order) throws InterruptedException, RemoteException, TransactionException, BadOrderException, UnusableEntryException{
	//	System.out.println(this.name + ": Processing order: " + order);
		//Find the part in the configuration manager, and see if it has any requirements
		Part part = PartConfigurationManager.getPart(order.partName);
		if (part == null){
			System.out.println(this.name + ": Unknown part: " + order.partName);
			return;
		}
		
		//Initialize our temporary holding lists
		ArrayList<String> waitingList = new ArrayList<String>();
		ArrayList<Part> receivedParts = new ArrayList<Part>();
		
		//Go through all the requirements and put an order out for each requirement
		for (int i = 0; i < part.requirements.size(); i++){
			Order newOrder = new Order(order.customerName, part.requirements.get(i), order.deadline);
			space.write(newOrder, null,  order.deadline);
			waitingList.add(newOrder.partName);
		}
		
		//Now wait for each part to be completed so that we can make our part.
		for (int i = 0; i < waitingList.size(); i++){
			Part temp = PartConfigurationManager.getPart(waitingList.get(i));
			temp.orderName = order.customerName;
			Part receivedPart = (Part) space.take(temp, null, TRANSACTION_TIME);
			if (receivedPart == null){
				System.out.println(this.name + ": Never got a " + temp.name);
			}
			else{
				System.out.println(this.name + ": Got a " + temp.name + "!");
				receivedParts.add(receivedPart);
			}
		}
		//If we got all our required parts
		if (receivedParts.size() == waitingList.size()){
			part.orderName = order.customerName;
			System.out.println(this.name + ": Building - " + part.name);
			Thread.sleep((long) (part.buildTime*1000));
			System.out.println(this.name + ": Completed - " + part.name);
			space.write(part, null, TRANSACTION_TIME);
		}
		//Else if we didn't
		else{
			System.out.println(this.name + ": Never got all required parts.");
		}
		
	}
	
	/**
	 * Overriden run funciton. Robot will get orders,
	 * and then process them, as long as running is true.
	 * 
	 * @author Andrew Thompson
	 */
	@Override
	public void run() {
		while (running){
			try {
				Order order = getOrder();
				if (order != null){
					processOrder(order);
				}
			} catch (RemoteException | UnusableEntryException
					| TransactionException | InterruptedException | BadOrderException e) {
				stopRunning();
				e.printStackTrace();
			}
		}
		
	}

}
