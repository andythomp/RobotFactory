/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;

import java.util.ArrayList;



/**
 * This class represents the answer to Question 1.
 * @author Andrew Thompson
 *
 */
public class BicycleFactory {

	/**
	 * This is the starting point to Question 1.
	 * It creates a bunch of robots with no lifespans, and puts them all to work.
	 * @param args
	 * @author Andrew Thompson
	 */
	public static void main(String[] args) {
		//Load up the policy file and the security manager.
		System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        //Initialize the part configuration manager
        PartConfigurationManager.init();
        
        //Create the primary robots
		OrderRobot orderRobot = new OrderRobot();
		ProductionRobot productionRobot = new ProductionRobot();
		PackingRobot packingRobot = new PackingRobot();
		
		//Create all the individual part robots.
		ArrayList<PartRobot> partRobots = new ArrayList<PartRobot>();
		partRobots.add(new PartRobot("BikeRobot", 0, "bicycle"));
		partRobots.add(new PartRobot("WheelRobot", 0, "wheel"));
		partRobots.add(new PartRobot("FrameRobot", 0, "frame"));
		partRobots.add(new PartRobot("TireRobot", 0, "tire"));
		partRobots.add(new PartRobot("RimRobot", 0, "rim"));
		partRobots.add(new PartRobot("SpokeRobot", 0, "spoke"));
		partRobots.add(new PartRobot("GARobot", 0, "gear_assembly"));
		partRobots.add(new PartRobot("GearRobot", 0, "gears"));
		partRobots.add(new PartRobot("CablesRobot", 0, "cables"));
		partRobots.add(new PartRobot("FrameRobot", 0, "frame"));
		partRobots.add(new PartRobot("BrakesRobot", 0, "brakes"));
		partRobots.add(new PartRobot("DiskRobot", 0, "disk"));
		partRobots.add(new PartRobot("SeatRobot", 0, "seat"));
		
		//Put all the robots to work
		new Thread(orderRobot).start();
		new Thread(productionRobot).start();
		new Thread(packingRobot).start();
		for (int i = 0; i < partRobots.size(); i++){
			new Thread(partRobots.get(i)).start();
		}
		
	}

}
