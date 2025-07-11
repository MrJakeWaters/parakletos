package org.parakletos;

// standard
import java.io.File;
import java.lang.Math;
import java.time.Duration;
import java.sql.Timestamp;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
						String start = String.valueOf(entry.get("entryStartTs"));
						String end = String.valueOf(entry.get("entryEndTs"));
						String duration = this.getFormattedDuration(start, end);
				
						// output formatting
						String idOutput = String.format("\n%sentry %s", Formatting.ANSI_YELLOW, entry.get("entryId"));
						String timestampOutput = String.format("\n%sDate: %s (%s)", Formatting.ANSI_WHITE, start, duration);
						String textOutput = String.format("\n\n  %s%s", Formatting.ANSI_CYAN, entry.get("text"));
						String output = String.format("%s%s%s%s", idOutput, timestampOutput, textOutput, Formatting.ANSI_RESET);
						System.out.println(output);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public String getFormattedDuration(String startTs, String endTs) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			// calculate and display duration
			float fractional_minutes = (float) (dateFormat.parse(endTs).getTime() - dateFormat.parse(startTs).getTime())/(1000*60);
			int minutes = (int) Math.floor(fractional_minutes);
			float seconds = (fractional_minutes - (float) minutes) * 60;
			if (minutes == 0) {
				return String.format("%s%s%s seconds%s%s", Formatting.BOLD_ON, Formatting.ANSI_PURPLE, (int) seconds, Formatting.ANSI_WHITE, Formatting.BOLD_OFF);
			} else {
				return String.format("%s%s%s minutes %s seconds%s%s", Formatting.BOLD_ON, Formatting.ANSI_PURPLE, minutes, (int) seconds, Formatting.ANSI_WHITE, Formatting.BOLD_OFF);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
}
