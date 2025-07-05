package org.prayer; 

import java.io.File;
import java.util.Random;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Init {
	// global statics
	public final static String WELCOME_MESSAGE = "Welcome to prayer, a cli journaling application that help you organize and express your thought and prayers to Jesus.";
	public final static String EXIT_MESSAGE = "\n[Philippians 4:19] And my God will supply every need of yours according to his riches in glory in Christ Jesus.";
	public final static String PRAYER_JOURNAL_CONFIG_HOME = String.format("%s/.config/prayer/", System.getProperty("user.home"));
	public final static String PRAYER_JOURNAL_CONFIG = String.format("%s/pjrc.json", Init.PRAYER_JOURNAL_CONFIG_HOME);
	public final static String PRAYER_CONFIG_HOME = String.format("%s/.config/prayer/", System.getProperty("user.home"));
	public final static String BIBLE_VERSION = "net";

	// dynamic
	public String header;
	public String bibleVerse;
	
	// constuctor
	public Init() {
		this.setHeader();
		this.appConfigurationSetup();
		this.setBibleVerse();
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
	public void setBibleVerse() {
		try {
			// converts InputStream to string through readAllBytes() method
			// prayers.json was scraped from https://api.biblesupersearch.com/api?bible=net&search=prayer
			ObjectMapper objectMapper = new ObjectMapper();
			String biblePrayerVersesJson = new String(getClass().getClassLoader().getResourceAsStream("prayers.json").readAllBytes());

			// get random verse within json prayer array objects to display
			JsonNode biblePrayerVerses = objectMapper.readTree(biblePrayerVersesJson).get("results");
			int n = new Random().nextInt(biblePrayerVerses.size()-1);

			// set random verse
			this.bibleVerse = this.getVerseText(biblePrayerVerses.get(n));
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
	
	// utility operations
	public String getVerseText(JsonNode node) {
		// converts JsonNode format of Bible Verse to string to display
		// see resource/prayers.json to see format
		String book_name = node.get("book_short").toString().replaceAll("\"","");
		String chapter = node.get("chapter_verse").toString().split(":")[0].replaceAll("\"","");
		String verse = node.get("chapter_verse").toString().split(":")[1].replaceAll("\"","");
		String text = node.get("verses").get(Init.BIBLE_VERSION).get(chapter).get(verse).get("text").toString().replaceAll("\"","");
		String bibleVerse = String.format("[%s %s:%s] %s", book_name, chapter, verse, text);
		return bibleVerse;
	}
	public void appConfigurationSetup() {
		// create configuration directory if it doesn't exist
		File directory = new File(Init.PRAYER_JOURNAL_CONFIG_HOME);
        if (!directory.exists()) {
            directory.mkdir();
		}

		// create configuration file if it doesn't exist
		File f = new File(Init.PRAYER_JOURNAL_CONFIG);
        if (!f.exists() && !f.isDirectory()) {
			SystemConfiguration config = new SystemConfiguration();
			Workflow workflow = new Workflow();
			// set encryption
			boolean encrypt = workflow.getUserInputBoolean("Does you want to encrypt your entries: [y/n]");
			config.setEncryption(encrypt);

			// set password protection
			boolean passwordProtection = workflow.getUserInputBoolean("Do you want password protection for access to your journal: [y/n]");
			config.setPasswordProtection(passwordProtection);
			if (config.getPasswordProtection()) {

				// set password
				String password = workflow.getUserInputWithValidation("Enter the password you want");
				config.setPassword(password);
			}

			// set journal directory configuration
			String prompt = String.format("Do you want to you the default directory to write entries (%s): [y/n]", SystemConfiguration.DEFAULT_JOURNAL_ENTRIES_DIRECTORY);
			boolean defaultEntryDirectory = workflow.getUserInputBoolean(prompt);
			if (!defaultEntryDirectory) {
				
				// set custom entries directory
				String entriesDir = workflow.getUserInput("Enter your desired directory");
				config.setEntriesDir(entriesDir);
			}
			
			workflow.closeScanner();


			// log object to console
			try {
				System.out.println(new ObjectMapper().writeValueAsString(config));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
}
