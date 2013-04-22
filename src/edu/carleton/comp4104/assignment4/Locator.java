/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;

//	 Jini core packages
import java.rmi.RMISecurityManager;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.Entry;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.lookup.entry.Name;
import net.jini.space.JavaSpace;

public class Locator {

	/* ------ FIELDS ------ */

	private String spaceName = null;
	private String jiniURL = null;
	private static Locator singleton;
	
	
	private JavaSpace space;

	/* ------ CONSTRUCTORS ------ */

	public Locator() {
		this("jini://localhost");
	}

	public Locator(String url) {
		this(null, url);
	}

	public Locator(String spaceName, String url) {

		this.jiniURL = url;
		this.spaceName = spaceName;
		init();
	}

	//
	// Initializes the space
	//
	private void init() {

		// Security manager

		if (System.getSecurityManager() == null) {
			try {
				System.setSecurityManager(new RMISecurityManager());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		LookupLocator locator = null;
		ServiceRegistrar registrar = null;

		try {
			// Get lookup service locator at "jini://hostname"
			// use default port and register of the locator
			locator = new LookupLocator(jiniURL);
			registrar = locator.getRegistrar();

			ServiceTemplate template;
			if (spaceName != null) {
				// Specify the service requirement, array (length 1) of
				// Entry interfaces (such as the Name interface)
				Entry[] attr = new Entry[1];
				attr[0] = new Name(spaceName);
				template = new ServiceTemplate(null, null, attr);
			} else {
				// Specify the service requirement, array (length 1) of
				// instances of Class
				Class<?>[] types = new Class[] { JavaSpace.class };
				template = new ServiceTemplate(null, types, null);
			}
			
			Object obj = registrar.lookup(template);
			if (obj instanceof JavaSpace) {
				space = (JavaSpace) obj;
			}
		} catch (Exception e) {
			System.err.println("JINI Registry not set up.");
		}
	}

	public static JavaSpace getSpace() {
		if (singleton == null) {
			singleton = new Locator();
			if (singleton.space == null) {
				singleton = null;
				return null;
			}
		}
		return singleton.space;
	}
	
}
