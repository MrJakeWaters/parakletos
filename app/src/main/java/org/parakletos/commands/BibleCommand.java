package org.parakletos;

// java
import java.io.File;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.io.IOException;
import java.io.FileNotFoundException;
// jackson
import com.fasterxml.jackson.databind.ObjectMapper;
// java parser
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class BibleCommand extends Command {
	public BibleSubCommand bibleUtil = new BibleSubCommand();
	public Map<String, Runnable> commands = new HashMap<>();
	public BibleCommand(String[] args) {
		super(args);
		
		// set functional hashmap for cli inputs
		this.commands.put("random-verse", () -> this.randomVerseCommand());
		this.commands.put("random-chapter", () -> this.randomChapterCommand());
		this.commands.put("get-chapter", () -> this.getChapterCommand());
		this.commands.put("daily-chapter", () -> this.dailyChapterCommand());
		this.commands.put("random-message", () -> this.randomMessageCommand());
		this.commands.put("set-version", () -> this.setVersionCommand());
		this.commands.put("help", () -> this.helpCommand());

	}
	public void execute() {
		// execute cli command
		if (this.commands.containsKey(this.function)) {
			this.commands.get(this.function).run();

		} else {
			System.out.println(String.format("The following command does not exist [%s]", this.function));
	
		}

	}
	public Map<String, Runnable> getCommands() {
		return this.commands;

	}
	public void randomVerseCommand() {
		// get a random verse, from a random book, from a random chapter in that book of the bible
		bibleUtil.showRandomBibleVerse();	

	}
	public void randomChapterCommand() {
		// get a random chapter from inside a random book of the bible
		bibleUtil.showRandomBibleChapter();	

	}
	public void getChapterCommand() {
		// get a specified chapter and book from the bible
		String book = String.join(" ", Arrays.copyOfRange(this.args, 0, this.args.length-1));
		int chapter = Integer.parseInt(this.args[this.args.length-1]);
		bibleUtil.showBibleChapter(book, chapter);
		
	}
	public void dailyChapterCommand() {
		// read a daily chapter of the bible
		bibleUtil.getDailyBibleChapter();

	}
	public void randomMessageCommand() {
		// get a random set of verses from a random book and random chapter of the bible
		bibleUtil.showRandomBibleMessage();

	}
	public void setVersionCommand() {
		// set a particular bible version from among various open source versions
		InitWorkflow config = new InitWorkflow();
		ObjectMapper mapper = new ObjectMapper();

		try {
			// Serialize Java object to JSON file
			mapper.writeValue(new File(Init.PK_CONFIG), config);

		} catch (IOException e) {
			e.printStackTrace();

		}

	}
	public void helpCommand() {
		// displays all commands options
		String filename = String.format("%s/src/main/java/org/parakletos/commands/BibleCommand.java", System.getProperty("user.dir"));
		HelpUtility hu = new HelpUtility(filename, this.commands);
		hu.setDescriptionMap();

		// display
		hu.display();

	}

}
