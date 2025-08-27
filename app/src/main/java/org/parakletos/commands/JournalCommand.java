package org.parakletos;

// java
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class JournalCommand extends Command {
	public Map<String, Runnable> commands = new HashMap<>();
	public JournalCommand(String[] args) {
		super(args);

		// functional hashmap
		this.commands.put("add", () -> this.addCommand());
		this.commands.put("read", () -> this.readCommand());
		this.commands.put("delete", () -> this.deleteCommand());
		this.commands.put("edit", () -> this.editCommand());
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
	public void addCommand() {
		// add a new journal entry
		new BibleSubCommand().showRandomBibleMessage();
		AddJournalEntrySubCommand subCommand = new AddJournalEntrySubCommand(this.args);

	}
	public void readCommand() {
		// read your journal entries
		ReadJournalEntriesSubCommand subCommand = new ReadJournalEntriesSubCommand(this.args);
		subCommand.getJournalContent().print();

	}
	public void deleteCommand() {
		// delete a journal entry
		DeleteJournalEntrySubCommand subCommand = new DeleteJournalEntrySubCommand(this.args);
		
	}
	public void editCommand() {
		// edit a journal entry
		EditJournalEntrySubCommand subCommand = new EditJournalEntrySubCommand(this.args);

	}
	public void helpCommand() {
		// displays all commands options
		String filename = String.format("%s/src/main/java/org/parakletos/commands/JournalCommand.java", System.getProperty("user.dir"));
		HelpUtility hu = new HelpUtility(filename, this.commands);
		hu.setDescriptionMap();

		// display
		hu.display();

	}
}
