package org.prayer;

// standard
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
// avro
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericDatumReader;


public class ReadJournalEntriesCommand extends Command {
	public ReadJournalEntriesCommand (String[] args) {
		super(args);
		this.setCommand("read-journal-entries");
		this.setDescription("reads your written journal entries");
		this.run();
	}
	public void run() {
		String entriesDir = String.valueOf(this.configs.get("entriesDir"));
		
		this.showEntries(entriesDir);
	}
	public void showEntries(String directory) {
		for (File f: new File(directory).listFiles()) {
			if (f.isDirectory()) {
				this.showEntries(f.getAbsolutePath());
			} else {
				Schema schema = ReflectData.get().getSchema(Entry.class);
				DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
				try {
					DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(f.getAbsolutePath()), reader);
					GenericRecord entry = null;
					while (dataFileReader.hasNext()) {
						entry = dataFileReader.next(entry);
						String output = String.format("\nEntry %s\nDate %s\n\t%s", entry.get("entryId"), entry.get("entryTimestamp"), entry.get("text"));
						System.out.println(output);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
