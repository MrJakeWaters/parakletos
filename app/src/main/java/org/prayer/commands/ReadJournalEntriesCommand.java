package org.prayer;

// standard
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class ReadJournalEntriesCommand extends Command {
	public ReadJournalEntriesCommand (String[] args) {
		super(args);
		this.setCommand("read-journal-entries");
		this.setDescription("reads your written journal entries");
		this.run();
	}
	public void run() {
		String entriesDir = String.format("%s/2025-07-08/4efa9cd5-581b-4902-9129-c9627658c92f", this.configs.get("entriesDir"));
		System.out.println(entriesDir);
	}
}
