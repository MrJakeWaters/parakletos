package org.parakletos;

// java
import java.io.File;
import java.util.Date;
import java.util.TimeZone;
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

public class ReadPrayerListSubCommand extends SubCommand {
	public String prayerDir;
	public ReadPrayerListSubCommand(String args[], String prayerDir) {
		super(args);
		this.prayerDir = prayerDir;
		this.setCommand("read-prayer-list");
		this.setDescription("reads list of people and things to prayer for");
		this.run();
	}
	public boolean prayerDirExists() {
		return !(new File(this.prayerDir)).isDirectory();
	}
	public String formatPrayerTimestamp(String ts) {
		try {
			// get parse format and set to UTC timezone
			SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parseFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			// convert to Local time with specific format
			SimpleDateFormat displayFormat = new SimpleDateFormat("EEE, MMM d h:mm a z");
			Date d = parseFormat.parse(ts);
			return String.valueOf(displayFormat.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	public void run() {
		if (this.prayerDirExists()) {
			System.out.println("No prayers found in prayer-list");
		} else {
			for (File f: new File(this.prayerDir).listFiles()) {
				Schema schema = ReflectData.get().getSchema(Prayer.class);
				DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
				try {
					DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(f.getAbsolutePath()), reader);
					GenericRecord prayer = null;
					while (dataFileReader.hasNext()) {
						prayer = dataFileReader.next(prayer);
						String ts = this.formatPrayerTimestamp(String.valueOf(prayer.get("prayerTs")));
				
						// output formatting
						String dateFormatting = String.format("%s%s%s", Formatting.BOLD_ON, Formatting.PURPLE, Formatting.ITALICS);
						String idOutput = String.format("\n%sprayerId %s (%s%s%s%s)", Formatting.YELLOW, prayer.get("prayerId"), dateFormatting, ts, Formatting.RESET, Formatting.YELLOW);
						String entity = String.format("\n%sFor: %s%s%s", Formatting.WHITE, Formatting.BOLD_ON, prayer.get("entity"), Formatting.RESET);
						String timestampOutput = String.format("\n%s%sDate: %s%s", Formatting.WHITE, Formatting.ITALICS, ts, Formatting.RESET);
						String textOutput = String.format("\n\n  %s%s", Formatting.CYAN, prayer.get("content"));
						String content = String.format("%s%s%s%s\n", idOutput, entity, textOutput, Formatting.RESET);
						System.out.println(content);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
