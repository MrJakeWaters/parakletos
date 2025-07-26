package org.parakletos;

// avro
import org.apache.avro.generic.GenericRecord;

public class EditJournalEntryWorkflow {
	public Workflow workflow = new Workflow();
	public Entry entry;
	public String filename;
	public EditJournalEntryWorkflow(GenericRecord record, String filename) {
		this.filename = filename;
		this.entry = new Entry(record);	
		System.out.println(String.format("%sCurrent Entry\n%s%s\n", Formatting.BOLD_ON, Formatting.RESET, this.entry.getText()));
		System.out.println(String.format("%s%sEnter update%s%s", Formatting.BOLD_ON, Formatting.CYAN, Formatting.RESET, Formatting.CYAN));
		this.updateAvroFile();
		System.out.println(String.format("%s",Formatting.RESET));
	}
	public void updateAvroFile() {
		this.entry.setText(workflow.getUserInput(""));
		Avro avro = new Avro();
		avro.write(this.filename, this.entry);
	}
}
