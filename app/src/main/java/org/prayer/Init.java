package org.prayer;
import java.io.IOException;

public class Init {
	public final static String WELCOME_MESSAGE = "Welcome to prayer, a cli journaling application that help you organize and express your thought to God.";
	public String header;
	public String bibleVerse;
	public Init() {
		this.setHeader();
		this.bibleVerse = "[Psalm 145:18] The Lord is near to all who call on him, to all who call on him in truth.";
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
