package org.parakletos;

// java
import java.io.File;
import java.lang.Math;
import java.util.HashMap;
import java.io.IOException;
// avro
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericDatumReader;

public class AddJournalEntryWorkflow {
	public HashMap prayerIds;
	public Entry entry;
	public String prayerDir;
	private Workflow workflow = new Workflow();

	public AddJournalEntryWorkflow(Entry entry) {
		this.prayerDir = String.format("%s/prayer-list/active", Init.PK_CONFIG_HOME).replaceAll("//","/");
		this.entry = entry;

		// Display and update entry
		String id = String.format("%sId%s: %s", Formatting.BOLD_ON, Formatting.BOLD_OFF, this.entry.getEntryId());

		// attaching a prayerId
		if (workflow.getUserInputBoolean("Do you want to attach a prayerId? [Y|n] ")) {
			this.readPrayerList();
			
			// get user input
			// user should return the index and not the actually uuid
			String prayerIdQuestion = "What prayerId do you want to use (enter number index)? ";
			String index = workflow.getUserInput(prayerIdQuestion);

			// ensure input from the user exists
			while (!this.prayerIds.containsKey(index)) {
				index = workflow.getUserInput("The prayerId index doesn't exist, try again ");
			}

			// map index to prayerId
			String prayerId = String.valueOf(this.prayerIds.get(index));
	
			// finalizing everything
			System.out.println(String.format("%s\nAttaching prayerId [%s] to entry", id, prayerId));
			this.entry.setPrayerId(prayerId);

		} else {
			System.out.println(String.format("%s\n%sCommitting Standard Entry%s", id, Formatting.BOLD_ON, Formatting.BOLD_OFF));
		}
	}
	public void readPrayerList() {
		// create hashmap for existing prayerIds
		this.prayerIds = new HashMap<String, String>();

		// display prayerIds
		int i = 1;
		for (File f: new File(this.prayerDir).listFiles()) {
			Schema schema = ReflectData.get().getSchema(Prayer.class);
			DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
			try {
				DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(f.getAbsolutePath()), reader);
				GenericRecord prayer = null;
				while (dataFileReader.hasNext()) {
					prayer = dataFileReader.next(prayer);
					int maxCharacters = Math.min(60, String.valueOf(prayer.get("content")).length());
			
					// output formatting
					String id = String.format("(%s%s%s)", Formatting.YELLOW, prayer.get("prayerId"), Formatting.RESET);
					String entity = String.format("[%s%s%s]", Formatting.BOLD_ON, prayer.get("entity"), Formatting.RESET);
					String content = String.format(" %s%s...%s", Formatting.CYAN, String.valueOf(prayer.get("content")).substring(0, maxCharacters), Formatting.RESET);
					String output = String.format("%s) %s%s%s", i, id, entity, content, Formatting.RESET);
					System.out.println(output);
					this.prayerIds.put(String.valueOf(i), prayer.get("prayerId")); 
					i += 1;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
