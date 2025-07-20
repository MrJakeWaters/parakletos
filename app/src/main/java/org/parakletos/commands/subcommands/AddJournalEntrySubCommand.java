package org.parakletos;

// java
import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.Field;
// avro
import org.apache.avro.Schema;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericDatumWriter;
// JLanguageTool
import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;
import org.languagetool.Languages;

public class AddJournalEntrySubCommand extends SubCommand {
	// https://kapilsreed.medium.com/apache-avro-demystified-66d80426c752
	public Entry entry;
	public AddJournalEntrySubCommand(String[] args) {
		// inhert
		super(args);
		
		// set base attributes and execute command
		this.setCommand("add-journal-entry");
		this.setDescription("Saves a journal entry with attached timestamp");
		this.run();
	}
	public void run() {
		// initialize entry and start workflow
		// this set uuid and start timestamp
		this.entry = new Entry();
		Workflow workflow = new Workflow();
		String content = workflow.getUserInput("\nEntry: ");

		// set end timestamp and entry content
		this.entry.setText(content);
		this.entry.setEntryEndTs();
		
		AddJournalEntryWorkflow ajew = new AddJournalEntryWorkflow(this.entry);
		// must be unique
		String fileName = String.format("%s/%s/%s.avro", this.configs.get("entriesDir"), this.executionDate, this.entry.getEntryId());
		
		// create writer and ensure path exists
		Avro avro = new Avro();
		avro.write(fileName, this.entry);
	};
}
