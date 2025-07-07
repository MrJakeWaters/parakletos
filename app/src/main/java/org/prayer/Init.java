package org.prayer; 

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Init {
	// global statics
	public final static String PJ_CONFIG_HOME = String.format("%s/.config/pj/", System.getProperty("user.home"));
	public final static String PJ_CONFIG = String.format("%s/pjrc.json", Init.PJ_CONFIG_HOME);
	public final static String PRAYER_CONFIG_HOME = String.format("%s/.config/pj/", System.getProperty("user.home"));
	public final static String BIBLE_VERSION = "net";

	// dynamic
	public String header;
	public String bibleVerse;
	
	// constuctor
	public Init() {
		// display application header
		this.setHeader();
		System.out.println(this.getHeader());

		// display bible
		this.setBibleVerse();
		System.out.println(this.getBibleVerse());

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
		System.out.println("\n");
		// create configuration directory if it doesn't exist
		File directory = new File(Init.PJ_CONFIG_HOME);
        if (!directory.exists()) {
            directory.mkdir();
		} else {
			System.out.println("Configuration Directory Setup");
		}

		// create configuration file if it doesn't exist
		File f = new File(Init.PJ_CONFIG);
        if (!f.exists() && !f.isDirectory()) {
			// console message and initialization
			System.out.println("Need to setup some of your configurations");
			SystemConfiguration config = new SystemConfiguration();
			config.setEntriesDir(SystemConfiguration.DEFAULT_PJ_ENTRIES_DIRECTORY);
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				// Serialize Java object to JSON file
				mapper.writeValue(new File(Init.PJ_CONFIG), config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Configuration File Already Setup");
		}
	}
}
