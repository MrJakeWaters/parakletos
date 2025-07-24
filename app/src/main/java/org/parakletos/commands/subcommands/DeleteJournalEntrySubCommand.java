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
		for (File f1: new File(String.valueOf(this.configs.get("entriesDir"))).listFiles()) {
			for (File f2: new File(f1.getAbsolutePath()).listFiles()) {
				if (f2.getName().equals(filename)) {
					f2.delete();
					return f2;
				}
			}
		}
		return new File("DoesNotExist");
	}
}
