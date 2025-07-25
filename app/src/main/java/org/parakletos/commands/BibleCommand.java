package org.parakletos;

// java
import java.io.File;
import java.util.Arrays;
import java.io.IOException;
// jackson
import com.fasterxml.jackson.databind.ObjectMapper;

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

		} else if (this.function.equals("--set-version")) {
			InitWorkflow config = new InitWorkflow();
			ObjectMapper mapper = new ObjectMapper();
			try {
				// Serialize Java object to JSON file
				mapper.writeValue(new File(Init.PK_CONFIG), config);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
