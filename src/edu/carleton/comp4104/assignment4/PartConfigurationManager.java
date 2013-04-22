/**
 * Group Identities:
 * Andrew Thompson, SN: 100745521
 * Roger Cheung, SN: 100741823
 * Chopel Tsering SN:100649290
 * 
 */
package edu.carleton.comp4104.assignment4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Static class that is responsible for storing all the information
 * about various parts that the system knows about.
 * @author Andrew Thompson
 *
 */
public class PartConfigurationManager {

	//JSON CONSTANTS
	private static final String FILE = "parts.prop";
	private static final String PATH = "parts/";
	private static final String NAME = "name";
	private static final String BUILD_TIME = "build_time";
	private static final String REQUIREMENTS = "requirements";
	
	//VARIABLES
	private static HashMap<String, Part> parts;
	private static boolean initialized = false;
	
	/**
	 * Returns a part from the list of parts when given the parts name.
	 * @param key - name of the part
	 * @return - a part if found, null if not
	 * @author Andrew Thompson
	 */
	public static Part getPart(String key){
		return parts.get(key);
	}
	
	/**
	 * Initializes the static part configuration manager, loading the parts property
	 * into memory, and then loading all the individual json files for each part into memory.
	 * After initialization is finished, the hashmap parts is filled with parts linked to their names.
	 * @author Andrew Thompson
	 */
	public static void init(){
		if (initialized){
			System.out.println(PartConfigurationManager.class.getSimpleName() + ": Already initialized.");
			return;
		}
		FileInputStream fileInput;
		Properties initConfig = new Properties();
		parts = new HashMap<String, Part>();
		try {
			//Load our initial configurations
			fileInput = new FileInputStream(PATH+FILE);
			initConfig.load(fileInput);
			fileInput.close();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<Object> partIterator = initConfig.values().iterator();
		while (partIterator.hasNext()){
			try {
				FileReader fileReader = new FileReader(PATH + (String) partIterator.next());
				JsonParser parser = new JsonParser();
				JsonObject o = (JsonObject) parser.parse(fileReader);
				Part part = new Part();
				part.name = o.get(NAME).getAsString();
				part.buildTime = o.get(BUILD_TIME).getAsFloat();
				JsonArray jsonRequirements = o.get(REQUIREMENTS).getAsJsonArray();
				for (int i = 0; i < jsonRequirements.size(); i++){
					String temp = jsonRequirements.get(i).getAsString();
					part.requirements.add(temp);
				}
				parts.put(part.name, part);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}
		System.out.println(PartConfigurationManager.class.getSimpleName() + ":Initialization complete.");
		System.out.println(PartConfigurationManager.class.getSimpleName() + ":Loaded " + parts.size() + " parts into memory.");
		initialized = true;
	}

}
