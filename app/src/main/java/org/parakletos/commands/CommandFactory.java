package org.parakletos;

public class CommandFactory {
	public Command command;
	public CommandFactory(String[] args) {
		if ((args[0].equals("journal"))) {
			if (args[1].equals("--add")) {
				this.command = new AddJournalEntryCommand(args);
			} else if (args[1].equals("--read")) {
				this.command = new ReadJournalEntriesCommand(args);
			}
		} else {
			this.command = new Command(args);
		}
		this.command.displayExitMessage();
	}
}
