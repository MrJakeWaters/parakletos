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
	public Map<String, Object> chapter_verses;
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
		int maxVerseNum = (int) this.chapter_verses.get(Integer.toString(chapter)); // get max verse number from object, can reference bibleBooks.json in resources folder
		int randomIndex = (new Random()).nextInt(maxVerseNum)+1; // ensure verse return is > 0;
		return randomIndex;
	}
	public String getRandomFullChapterReference() {
		// return reference string (for api call) for the entire chapter
		// this consists of the first verse to the last
		// i.e  Mark 5:1-43 (43 verses in Mark chapter 5)
		int chapter = this.getRandomChapter();
		return this.getFullChapterReference(chapter);
	}
	public String getFullChapterReference(int chapter) {
		int lastVerse = (int) this.chapter_verses.get(Integer.toString(chapter)); // get max verse number from object, can reference bibleBooks.json in resources folder
		return String.format("%s %s:1-%s", this.getShortname(), chapter, lastVerse);
	}
}
