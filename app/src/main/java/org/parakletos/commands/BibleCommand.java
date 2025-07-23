package org.parakletos;

// java
import java.util.Arrays;

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
			String book = String.join(" ", Arrays.copyOfRange(this.args, 0, this.args.length-1));
			int chapter = Integer.parseInt(this.args[this.args.length-1]);
			bibleUtil.showBibleChapter(book, chapter);
		} else if (this.function.equals("--daily-chapter")) {
			// save and retreive daily chapter for reading
			bibleUtil.getDailyBibleChapter();
		}
	}
}
