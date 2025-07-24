package org.parakletos;

// java
import java.lang.NumberFormatException;

public class AddJournalEntryWorkflow {
	public Entry entry;
	private Workflow workflow = new Workflow();

	public AddJournalEntryWorkflow(Entry entry) {
		this.entry = entry;

		// Display and update entry
		String id = String.format("%sId%s: %s", Formatting.BOLD_ON, Formatting.BOLD_OFF, this.entry.getEntryId());
		System.out.println(String.format("%s\n%sCommitting Entry%s: %s", id, Formatting.BOLD_ON, Formatting.BOLD_OFF, this.entry.getText()));
	}
}
