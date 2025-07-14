package org.parakletos; 

// java
import java.io.File;
import java.util.Map;
import java.util.Random;
import java.io.IOException;
// jackson
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Init {
	// global statics
	public final static String PK_CONFIG_HOME = String.format("%s/.config/pk/", System.getProperty("user.home"));
	public final static String PK_CONFIG = String.format("%s/pkrc.json", Init.PK_CONFIG_HOME);
	public final static String PRAYER_CONFIG_HOME = String.format("%s/.config/pk/", System.getProperty("user.home"));

	// dynamic
	public String header;
	public String bibleVerse;
	
	// constuctor
	public Init() {
		// display application header
		this.setHeader();
		System.out.println(this.getHeader());

		// check for configrations
		this.appConfigurationSetup();
	}

	// setters
	public void setHeader() {
		try {
			// converts InputStream to string through readAllBytes() method
			this.header = new String(getClass().getClassLoader().getResourceAsStream("header.txt").readAllBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// getters
	public String getHeader() {
		return this.header; 
	}
	
	public void appConfigurationSetup() {
		// create configuration directory if it doesn't exist
		File directory = new File(Init.PK_CONFIG_HOME);
        if (!directory.exists()) {
            directory.mkdir();
		}

		// create configuration file if it doesn't exist
		File f = new File(Init.PK_CONFIG);
        if (!f.exists() && !f.isDirectory()) {
			InitWorkflow config = new InitWorkflow();
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				// Serialize Java object to JSON file
				mapper.writeValue(new File(Init.PK_CONFIG), config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
