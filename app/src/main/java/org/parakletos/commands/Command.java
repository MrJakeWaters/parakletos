package org.parakletos;

import java.io.File;
import java.util.Map;
import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Command {
	public String[] args;
	public String command;
	public String description;
	public String exitMessage;
	public Map<String, Object> configs;
	public String executionDate; // allows for each command to have easy access to date partition for writing to disk (if it uses it)

	// constructor
	public Command(String[] args) {
		this.args = args;
		this.command = "";
		this.setConfigs();
		this.executionDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	// customizable methods
	public void setArgs() {};
	public void run() { };
	public void displayExitMessage() {
		System.out.println(String.format("\n[INFO] %s command completed successfully", (this.command)));	
	}

	// base methods for all commmands
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public void setConfigs() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			// Serialize Java object to JSON file
			// get journal entries location from pkrc file
			this.configs = mapper.readValue(new File(Init.PK_CONFIG), Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
