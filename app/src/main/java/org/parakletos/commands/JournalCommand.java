package org.parakletos;

// java
import java.util.Arrays;

public class JournalCommand extends Command {
	public JournalCommand(String[] args) {
		super(args);

		// execution
		if (this.function.equals("--add")) {
			this.subCommand = new AddJournalEntrySubCommand(this.args);
		} else if (this.function.equals("--read")) {
			this.subCommand = new ReadJournalEntriesSubCommand(this.args);
		} else {
			System.out.println("No command specified");
		}
	}
}
