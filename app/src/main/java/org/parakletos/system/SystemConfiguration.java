package org.parakletos; 

public class SystemConfiguration {
	// statics
	public final static String PK_CONFIG_HOME = String.format("%s/.config/pk/", System.getProperty("user.home"));
	public final static String DEFAULT_PK_ENTRIES_DIRECTORY = String.format("%s/.config/pk/entries", System.getProperty("user.home"));
	// attributes
	public String entriesDir;

	// constructors
	public void SystemConfiguration () { }
	public void SystemConfiguration (String password, boolean encryption, String entriesDir) {
		this.entriesDir = entriesDir;
	}
	
	public void setEntriesDir(String entriesDir) {
		this.entriesDir = entriesDir;
	}
	
	public String getEntriesDir() {
		return this.entriesDir;
	}
}
