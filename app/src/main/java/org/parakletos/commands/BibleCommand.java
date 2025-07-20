package org.parakletos;

public class BibleCommand extends Command {
	public BibleSubCommand bibleUtil = new BibleSubCommand();
	public BibleCommand(String[] args) {
		super(args);
		
		// execution
		if (this.function.equals("--random-verse")) {
			bibleUtil.showRandomBibleVerse();
		} else if (this.function.equals("--random-chapter")) {
			bibleUtil.showRandomBibleChapter();
		} else if (this.function.equals("--get-chapter")) {
			String book = this.args[0];
			int chapter = Integer.parseInt(this.args[1]);
			bibleUtil.showBibleChapter(book, chapter);
		}
	}
}
