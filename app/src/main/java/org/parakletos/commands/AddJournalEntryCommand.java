package org.parakletos;

// java
import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.Field;
// jackson json objects
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
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

public class AddJournalEntryCommand extends Command {
	// https://kapilsreed.medium.com/apache-avro-demystified-66d80426c752
	public Entry entry;
	public AddJournalEntryCommand(String[] args) {
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
		
		// ensure entries and partition of entry directories exist
		String entriesDir = String.valueOf(this.configs.get("entriesDir"));
		File directory = new File(entriesDir);
        if (!directory.exists()) {
            directory.mkdir();
			System.out.println(String.format("[INFO] Entries Directory Created: %s", entriesDir));
		}

		// create entries partition directory exists
		String entriesDatePartitionDir = String.format("%s/%s/", entriesDir, this.executionDate);
		File partitionDirectory = new File(entriesDatePartitionDir);
		if (!partitionDirectory.exists()) {
			partitionDirectory.mkdir();
			System.out.println(String.format("[INFO] New Partition Created: %s", entriesDir));
		}

		// must be unique
		String fileName = String.format("%s/%s/%s.avro", this.configs.get("entriesDir"), this.executionDate, this.entry.getEntryId());
        Schema schema = ReflectData.get().getSchema(Entry.class);

		// convert java class to generic record for SpecificDatumWriter
		Map<String, Object> entryMap = new AvroAbstract(this.entry).getMap();
		GenericRecord avroRecord = new GenericData.Record(schema);
		schema.getFields().forEach(field -> {
			avroRecord.put(field.name(), entryMap.get(field.name()));
		});

		// create avro writer
		DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(writer);

		// set data record schema to writer
		try {
			dataFileWriter.create(schema, new File(fileName));

			// add object to writer and write
			dataFileWriter.append(avroRecord);
			dataFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	};
}
