package org.prayer;

public class CommandFactory {
	public Command command;
	public CommandFactory(String[] args) {
		if (args.length == 0) {
			this.command = new AddJournalEntryCommand(args);
		} else {
			this.command = new Command(args);
		}
		this.command.displayExitMessage();
	}
}
