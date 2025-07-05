package org.prayer;

public class CommandFactory {
	public CommandFactory(String[] args) {
		if (args.length == 0) {
			AddJournalEntryCommand command = new AddJournalEntryCommand(args);
		} else {
			System.out.println("Do something else");
		}
	}
}
