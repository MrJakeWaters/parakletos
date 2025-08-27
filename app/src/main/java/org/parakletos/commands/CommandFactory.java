package org.parakletos;

// java
import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.ArrayList;
import java.io.FileNotFoundException;
// java parser
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class CommandFactory {
	/* 
	 * Factor for all available cli commands
	 * Implemented through functional hashmap to improve readability and help command
	 * All commands must map to a method name with syntax <method name>Command
		 *  the first command of these methods is what is display in the help command
	 */
	public String function;
	public Map<String, Runnable> commands = new HashMap<>();
	public String[] args;
	public CommandFactory(String[] args) {
		this.function = args[0];
		this.args = Arrays.copyOfRange(args, 1, args.length);

		// functional hashmap for cli inputs
		this.commands.put("journal", () -> this.journalCommand());	
		this.commands.put("bible", () -> this.bibleCommand());	
		this.commands.put("prayer", () -> this.prayerCommand());
		this.commands.put("help", () -> this.helpCommand());

	}
	public Map<String, Runnable> getCommands() {
		return this.commands;

	}
	public void execute() {
		// execute cli command
		if (this.commands.containsKey(this.function)) {
			this.commands.get(this.function).run();

		} else {
			System.out.println(String.format("The following command does not exist [%s]", this.function));
	
		}

	}
	public void journalCommand() {
		// entry point for all journal commands
		JournalCommand command = new JournalCommand(this.args);
		command.execute();

	}
	public void bibleCommand() {
		// entry point for all bible commands
		BibleCommand command = new BibleCommand(this.args);
		command.execute();
	
	}
	public void prayerCommand() {
		// entry point for all prayer commands
		PrayerCommand command = new PrayerCommand(this.args);
		command.execute();

	}
	public void helpCommand() {
		// displays available commands with description
		String filename = String.format("%s/src/main/java/org/parakletos/commands/CommandFactory.java", System.getProperty("user.dir"));
		HelpUtility hu = new HelpUtility(filename, this.commands);
		hu.setDescriptionMap();

		// display
		hu.display();

	}

}
