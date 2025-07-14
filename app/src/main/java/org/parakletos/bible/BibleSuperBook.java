package org.parakletos;

// java
import java.util.List;
import java.util.Random;
import java.util.Map;

public class BibleSuperBook {
	public int id;
	public String name;
	public String shortname;
	public int chapters;
	private Map<String, Object> chapter_verses;
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getShortname() {
		return this.shortname;
	}
	public int getChapters() {
		return this.chapters;
	}
	public Map<String, Object> getChapter_Verses() {
		return this.chapter_verses;
	}
	public int getRandomChapter() {
		int randomIndex = (new Random()).nextInt(this.chapters)+1; // ensure verse return is > 0;
		return randomIndex;
	}
	public int getRandomVerse(int chapter) {
		int randomIndex = (new Random()).nextInt(chapter)+1; // ensure verse return is > 0;
		return randomIndex;
	}
}
