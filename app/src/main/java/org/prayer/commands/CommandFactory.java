package org.prayer;

public class CommandFactory {
	public Command command;
	public CommandFactory(String[] args) {
		if ((args[0].equals("journal"))) {
			this.command = new AddJournalEntryCommand(args);
		} else if (args[0].equals("read")) {
			this.command = new ReadJournalEntriesCommand(args);
		} else {
			this.command = new Command(args);
		}
		this.command.displayExitMessage();
	}
}
