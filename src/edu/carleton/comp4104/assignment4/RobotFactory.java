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
 * This class represents the answer to Question 2.
 * @author Andrew Thompson
 *
 */
public class RobotFactory {

	/**
	 * This is the starting point to Question 2.
	 * @param args - unused
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
		partRobots.add(new PartRobot("RobotRobot", 0, "robot"));
		partRobots.add(new PartRobot("ArmRobot", 0, "arm"));
		partRobots.add(new PartRobot("FingerRobot", 0, "finger"));
		partRobots.add(new PartRobot("CameraRobot", 0, "camera"));
		partRobots.add(new PartRobot("BrainRobot", 0, "brain"));
		partRobots.add(new PartRobot("JointRobot", 0, "joint"));
		partRobots.add(new PartRobot("ArmSegRobot", 0, "arm_segment"));
		
		//Put all the robots to work
		new Thread(orderRobot).start();
		new Thread(productionRobot).start();
		new Thread(packingRobot).start();
		for (int i = 0; i < partRobots.size(); i++){
			new Thread(partRobots.get(i)).start();
		}
		
	}

}
