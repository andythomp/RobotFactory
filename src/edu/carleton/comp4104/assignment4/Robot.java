/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;

import java.util.Timer;
import java.util.TimerTask;
import net.jini.space.JavaSpace;

/**
 * Template for a Robot, contains everything
 * a Robot needs to do it's job regardless of what kind
 * of robot it is.
 * @author Andrew Thompson
 *
 */
public abstract class Robot implements Runnable{

	public static final long TRANSACTION_TIME = 100000;
	
	public long lifespan; // lifespan of 0 means run forever
	public String name;
	public String config;
	public boolean running;
	public JavaSpace space;
	public Timer timer;
	
	
	/**
	 * Creates a robot given a set of information.
	 * 
	 * @param name - Name of the robot
	 * @param lifespan - Time the robot will run for. Value of 0 means run forever.
	 * @param config - Part specifications
	 * @author Andrew Thompson
	 */
	public Robot(String name, long lifespan, String config){
		this.lifespan = lifespan;
		this.name = name;
		this.config = config;
		running = true;
		timer = new Timer();
		this.space = Locator.getSpace();
		//Set a timer task to flag running as false after the lifespan has run out.
		if (lifespan != 0){
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					stopRunning();
				}
			}, lifespan);
		}
	}
	
	/**
	 * Should not be used to create a robot in a meaningful manner.
	 * This should be strictly used to create Robot templates
	 * 
	 * @author Andrew Thompson
	 */
	public Robot(){
		this("", 0, null);
		
	}

	/**
	 * Flags the robot to stop running.
	 * @author Andrew Thompson
	 */
	public void stopRunning(){
		System.out.println(this.getClass().getSimpleName() + ":Shutting down:" + this.getClass().getSimpleName());
		running = false;
	}
	
	/**
	 * Defines what a robot does when it is running. Should be overridden by
	 * every robot implementation.
	 * @author Andrew Thompson
	 */
	@Override
	public abstract void run();
	
	
}
