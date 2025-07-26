package org.parakletos;

// java
import java.io.File;
import java.util.List;
// avro
import org.apache.avro.generic.GenericRecord;

public class EditJournalEntrySubCommand extends SubCommand {
	public String entryId;
	public EditJournalEntrySubCommand(String[] args) {
		super(args);
		this.entryId = this.args[this.args.length-1];
		this.run();
	}
	public void run () {
		File f = this.getEntryFile();
		if (f.getName().equals("DoesNotExist")) {
			System.out.println(String.format("Entry Not Found: %s", this.entryId));
		} else {
			Avro avro = new Avro();
			List<GenericRecord> records = avro.getRecords(f.getAbsolutePath(), Entry.class);
			GenericRecord record = records.get(0);
			EditJournalEntryWorkflow workflow = new EditJournalEntryWorkflow(record, f.getAbsolutePath());
		}
	}
	public File getEntryFile() {
		String entriesDir = String.valueOf(this.configs.get("entriesDir"));
		for (File f: new File(entriesDir).listFiles()) {
			File testFile = new File(String.format("%s/%s.avro", f.getAbsolutePath(), this.entryId));
			if (testFile.exists()) {
				return testFile;
			}
		}
		return new File("DoesNotExist");
	}
}
