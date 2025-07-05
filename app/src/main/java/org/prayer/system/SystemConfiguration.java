package org.prayer; 

public class SystemConfiguration {
	// statics
	public final static String PRAYER_JOURNAL_CONFIG_HOME = String.format("%s/.config/prayer/", System.getProperty("user.home"));
	public final static String DEFAULT_JOURNAL_ENTRIES_DIRECTORY = String.format("%s/.config/prayer/journal/entries", System.getProperty("user.home"));
	// attributes
	public String password;
	public boolean passwordProtection;
	public boolean encryption;
	public String entriesDir;

	// constructors
	public void SystemConfiguration () { }
	public void SystemConfiguration (String password, boolean encryption, String entriesDir) {
		this.password = password;
		this.encryption = encryption;
		this.entriesDir = entriesDir;
	}
	
	// setters
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPasswordProtection(boolean passwordProtection) {
		this.passwordProtection = passwordProtection;
	}
	public void setEncryption(boolean encryption) {
		this.encryption = encryption;
	}
	public void setEntriesDir(String entriesDir) {
		this.entriesDir = entriesDir;
	}
	
	// getters
	public String getPassword(String password) {
		return this.password;
	}
	public boolean getPasswordProtection() {
		return this.passwordProtection;
	}
	public boolean getEncryption() {
		return this.encryption;
	}
	public String getEntriesDir() {
		return this.entriesDir;
	}
}
