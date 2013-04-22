/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;

import java.util.ArrayList;

import net.jini.core.entry.Entry;


/**
 * A JavaSpace entry that represents a part.
 * The part is anonymous and can be of either robots
 * or bicycles. 
 * @author Andrew Thompson
 *
 */
public class Part implements Entry{

	/**
	 * @author Andrew Thompson
	 */
	private static final long serialVersionUID = -3657459828688819915L;
	
	
	public String name;
	public Float buildTime;
	public ArrayList<String> requirements;
	public String orderName;
	
	/**
	 * Creates a new part. This function should be used sparingly.
	 * If a new part needs to be created to compare or otherwise, it is much better to use
	 * the PartConfigurationManager to create the part as it will lay the parts original template
	 * down.
	 * @author Andrew Thompson
	 */
	public Part(){
		requirements = new ArrayList<String>();
	}

}
