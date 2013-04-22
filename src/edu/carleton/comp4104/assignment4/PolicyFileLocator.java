/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;
/**

 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
import java.io.File;

/**
 * Looks up the policy file in the default eclipse directory.
 * @author Andrew Thompson
 */
public class PolicyFileLocator {
    public static final String POLICY_FILE_NAME = "policy.all";
    public static String getLocationOfPolicyFile() {
   
    	File file = new File(POLICY_FILE_NAME);
    	return file.getAbsolutePath();
    }
}
