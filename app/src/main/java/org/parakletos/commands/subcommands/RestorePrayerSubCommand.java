package org.parakletos;

// java
import java.io.File;
import java.util.List;
// avro
import org.apache.avro.generic.GenericRecord;

public class RestorePrayerSubCommand extends SubCommand {
	public String activePrayerDir;
	public String archivePrayerDir;
	public String prayerId;
	public RestorePrayerSubCommand(String[] args, String activePrayerDir, String archivePrayerDir) {
		super(args);
		this.prayerId = this.args[0];
		this.activePrayerDir = activePrayerDir;
		this.archivePrayerDir = archivePrayerDir;
		this.run();

	}
	public void run() {
		// get current and new file directories
		String activeFilename = String.format("%s/%s.avro", this.activePrayerDir, this.prayerId);
		String archiveFilename = String.format("%s/%s.avro", this.archivePrayerDir, this.prayerId);

		if (new File(archiveFilename).exists()) {
			// generate success statement that ouputs prayer
			Avro avro = new Avro();
			List<GenericRecord> records = avro.getRecords(archiveFilename, Prayer.class);
			GenericRecord prayer = records.get(0);
			
			boolean isMoved = new File(archiveFilename).renameTo(new File(activeFilename));
			String output = String.format("Prayer [%s] for [%s] Restored\n%s", prayer.get("prayerId"), prayer.get("entity"), prayer.get("content"));
			System.out.println(output);

		} else {
			String output = String.format("Prayer [%s] is not exist in your archive", this.prayerId);
			System.out.println(output);

		}
	}
}
