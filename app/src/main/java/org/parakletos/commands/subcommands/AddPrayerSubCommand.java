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

public class AddPrayerSubCommand extends SubCommand {
	public Prayer prayer;
	public String prayerDir;
	public AddPrayerSubCommand(String[] args, String prayerDir) {
		super(args);
		this.prayerDir = prayerDir;
		this.setCommand("add-prayer");
		this.setDescription("Add prayer to prayer list");
		this.run();
	}
	public void run() {
		// adding prayer workflow
		this.prayer = new Prayer();
		Workflow workflow = new Workflow();
		
		// set who the prayer is for
		String entity = workflow.getUserInput("\nWho or what do you want to prayer for? ");
		this.prayer.setEntity(entity);

		// save the content of the prayer
		String content = workflow.getUserInput("\nPrayer: ");
		this.prayer.setContent(content);

		// must be unique
		String fileName = String.format("%s/%s.avro", this.prayerDir, this.prayer.getPrayerId());

		// create writer and ensure path exists
		Avro avro = new Avro();
		avro.write(fileName, this.prayer);
	}
}
