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
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

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
						String idOutput = String.format("\n%sentry %s", ReadJournalEntriesCommand.ANSI_YELLOW, entry.get("entryId"));
						String timestampOutput = String.format("\n%sDate: %s", ReadJournalEntriesCommand.ANSI_WHITE, entry.get("entryTimestamp"));
						String textOutput = String.format("\n\n  %s%s", ReadJournalEntriesCommand.ANSI_CYAN, entry.get("text"));
						String output = String.format("%s%s%s%s", idOutput, timestampOutput, textOutput, ReadJournalEntriesCommand.ANSI_RESET);
						System.out.println(output);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
