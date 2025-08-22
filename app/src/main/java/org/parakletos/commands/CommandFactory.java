package org.parakletos;

// java
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class CommandFactory {
	public Command command;
	public String function;
	public Map<String, Runnable> commands = new HashMap<>();
	public String[] args;
	public CommandFactory(String[] args) {
		this.function = args[0];
		this.args = Arrays.copyOfRange(args, 1, args.length);

		// generate functional hashmap for cli inputs
		this.commands.put("journal", () -> this.journalCommand());	
		this.commands.put("bible", () -> this.bibleCommand());	
		this.commands.put("prayer", () -> this.prayerCommand());	

		// execute cli command
		if (this.commands.containsKey(this.function)) {
			this.commands.get(this.function).run();

		} else {
			System.out.println(String.format("The following function does not exist [%s]", this.function));
	
		}
		
	}
	public void journalCommand() {
		this.command = new JournalCommand(this.args);

	}
	public void bibleCommand() {
		this.command = new BibleCommand(this.args);
	
	}
	public void prayerCommand() {
		this.command = new PrayerCommand(this.args);

	}
}
