package org.prayer;

public class AddJournalEntryCommand extends Command {
	public Entry entry;
	public AddJournalEntryCommand(String[] args) {
		super(args);
		this.setCommand("add-journal-entry");
		this.setDescription("Saves a journal entry with attached timestamp");
		this.run();
	}
	public void run() {
		Workflow workflow = new Workflow();
		String entryText = workflow.getUserInput("\nEntry: ");
		this.entry = new Entry(entryText);
		String writeDir = String.format("%s/%s", this.configs.get("entriesDir"), this.executionDate);
		String fullFileName = String.format("%s/%s.orc", writeDir, this.entry.getEntryTimestamp().toString().replace(":","_"));

		// create orc writer
		OrcWriter orc = new OrcWriter(this.entry);
		orc.writeFile(fullFileName, entry.getEntryTimestamp());
	};
}
