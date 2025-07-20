package org.parakletos; 

// standard
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.lang.reflect.Field;

public class InitWorkflow {
	// workflow that gets all needed configurations from the user to 
	// begin using the app
	public String bibleVersion;
	public String entriesDir;
	private Map<String, String> bibleVersionMap;
	private Workflow workflow = new Workflow();

	// constructor
	public InitWorkflow() {
		// get bible version
		this.bibleVersionMap = new HashMap<>();
		String bibleVersionId = workflow.getUserInput(this.bibleVersionsPrompt());
		this.bibleVersion = this.bibleVersionMap.get(bibleVersionId).toLowerCase();

		// set default write directory
		this.entriesDir = SystemConfiguration.DEFAULT_PK_ENTRIES_DIRECTORY;
	}
	public String bibleVersionsPrompt() {
		String prompt = "  Selection     Version\n-------------------------\n";
		int i = 1;
		for (Field field: new Bibles().getClass().getDeclaredFields()) {
			field.setAccessible(true);
			int versionLength = field.getName().length();
			prompt = String.format("%s  %s\t\t\t\t%s\n", prompt, String.valueOf(i), field.getName());
			this.bibleVersionMap.put(String.valueOf(i), field.getName());
			i += 1;
		}	
		prompt = String.format("%s\nSelect the bible version number you want: ", prompt);
		return prompt;
	}
}
