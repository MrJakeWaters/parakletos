package org.parakletos;

public class BibleSubCommand {
	public BibleSuperSearchApi functions;
	public BibleSubCommand () {
		this.functions = new BibleSuperSearchApi();	
	}
	public void showRandomBibleVerse() {
		String verseReference = this.functions.getRandomBibleVerseReference();
		String content = this.functions.getBibleVerse(verseReference.replace(" ", "%20"));
		System.out.println(content);
	}
	public void showRandomBibleChapter() {
		String verseReference = this.functions.getRandomBibleChapterReference();
		String content = this.functions.getBibleVerse(verseReference.replace(" ", "%20"));
		System.out.println(content);
	}
	public void showBibleChapter(String book, int chapter) {
		String verseReference = this.functions.getBibleBookChapterReference(book, chapter);
		String content = this.functions.getBibleVerse(verseReference.replace(" ", "%20"));
		System.out.println(content);
	}
}
