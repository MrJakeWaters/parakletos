package org.prayer; 

public class SystemConfiguration {
	// statics
	public final static String PJ_CONFIG_HOME = String.format("%s/.config/pj/", System.getProperty("user.home"));
	public final static String DEFAULT_PJ_ENTRIES_DIRECTORY = String.format("%s/.config/pj/entries", System.getProperty("user.home"));
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
