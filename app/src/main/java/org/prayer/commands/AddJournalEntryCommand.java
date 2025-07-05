package org.prayer;
import java.util.Scanner;

public class AddJournalEntryCommand extends Command {
	public String entry;
	public AddJournalEntryCommand(String[] args) {
		super(args);
		this.setCommand("add-journal-entry");
		this.setDescription("Saves a journal entry with attached timestamp");
		this.run();
	}
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Write your entry. (press Ctrl+D to save)");

		// wait for Ctrl+D
		this.entry = "\n\nYour Entry: ";
		while (scanner.hasNextLine()) {
			this.entry = String.format("%s%s", this.entry, scanner.nextLine());
		}
		scanner.close();
		System.out.println(String.format("%s\n", this.entry));
	};
}
