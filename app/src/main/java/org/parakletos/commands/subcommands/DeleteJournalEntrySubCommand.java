package org.parakletos;

// java
import java.io.File;

public class DeleteJournalEntrySubCommand extends SubCommand {
	public String entryId;
	public DeleteJournalEntrySubCommand (String[] args) {
		super(args);
		this.entryId = this.args[0];
		this.run();
	}
	public void run() {
		String filename = String.format("%s.avro", this.entryId);
		File f = this.getFile(filename);
		if (f.getName().equals("DoesNotExist")) {
			System.out.println(String.format("No such entry found: %s", this.entryId));	
		} else {
			System.out.println(String.format("Entry Deleted: %s", this.entryId));	
		}
	}
	public File getFile(String filename) {
		for (File f: new File(String.valueOf(this.configs.get("entriesDir"))).listFiles()) {
			File test = new File(String.format("%s/%s", f.getAbsolutePath(), filename));
			if (test.exists()) {
				return test;	
			}
		}
		return new File("DoesNotExist");
	}
}
