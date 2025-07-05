package org.prayer;
import java.io.File;
import java.util.Random;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Init {
	public final static String WELCOME_MESSAGE = "Welcome to prayer, a cli journaling application that help you organize and express your thought and prayers to our Lord.";
	public final static String PRAYER_CONFIG_HOME = String.format("%s/.config/prayer/", System.getProperty("user.home"));
	public final static String BIBLE_VERSION = "net";
	private static String appConfigHome;
	public String header;
	public String bibleVerse;
	public Init() {
		this.setHeader();
		this.appDirectoryConfigCreator();
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
		String bibleVerse = String.format("\n[%s %s:%s] %s", book_name, chapter, verse, text);
		return bibleVerse;
	}
	public void appDirectoryConfigCreator() {
		File directory = new File(Init.PRAYER_CONFIG_HOME);
        if (!directory.exists()) {
            directory.mkdir();
		}
	}

}
