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

		// attaching a prayerId
		if (workflow.getUserInputBoolean("Do you want to attach a prayerId? [Y|n] ")) {
			String prayerIdQuestion = "What prayerId do you want to use?";
			System.out.println("coming soon...");
		} else {
			System.out.println(String.format("%s\n%sCommitting Standard Entry%s", id, Formatting.BOLD_ON, Formatting.BOLD_OFF));
		}
	}
}
