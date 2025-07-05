package org.prayer;
import java.io.File;
import java.io.IOException;

public class Init {
	public final static String WELCOME_MESSAGE = "Welcome to prayer, a cli journaling application that help you organize and express your thought to God.";
	public final static String PRAYER_CONFIG_HOME = String.format("%s/.config/prayer/", System.getProperty("user.home"));
	private static String appConfigHome;
	public String header;
	public String bibleVerse;
	public Init() {
		this.setHeader();
		this.appDirectoryConfigCreator();
		this.bibleVerse = "[Psalm 145:18] The Lord is near to all who call on him, to all who call on him in truth.";
	}

	// create app config directory if it doesn't already exist
	public void appDirectoryConfigCreator() {
		File directory = new File(Init.PRAYER_CONFIG_HOME);
        if (!directory.exists()) {
            directory.mkdir();
		}
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
	public String getBibleVerse() {
		return this.bibleVerse;
	}
}
