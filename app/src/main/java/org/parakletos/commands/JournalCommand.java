package org.parakletos;

// java
import java.util.Arrays;

public class JournalCommand extends Command {
	public JournalCommand(String[] args) {
		super(args);

		// execution
		if (this.function.equals("--add")) {
			// show random bible verse
			new BibleSubCommand().showRandomBibleMessage();
			AddJournalEntrySubCommand subCommand = new AddJournalEntrySubCommand(this.args);
		} else if (this.function.equals("--read")) {
			ReadJournalEntriesSubCommand subCommand = new ReadJournalEntriesSubCommand(this.args);
			subCommand.getJournalContent().print();
		} else if (this.function.equals("--delete")) {
			DeleteJournalEntrySubCommand subCommand = new DeleteJournalEntrySubCommand(this.args);
		} else {
			System.out.println("No command specified");
		}
	}
}
