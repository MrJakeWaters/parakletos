package org.parakletos;

// java
import java.io.File;
import java.util.List;
// avro
import org.apache.avro.generic.GenericRecord;

public class ArchivePrayerSubCommand extends SubCommand {
	public String activePrayerDir;
	public String archivePrayerDir;
	public String prayerId;
	public ArchivePrayerSubCommand(String[] args, String activePrayerDir, String archivePrayerDir) {
		super(args);
		this.prayerId = this.args[0];
		this.activePrayerDir = activePrayerDir;
		this.archivePrayerDir = archivePrayerDir;
		if (!new File(this.archivePrayerDir).exists()) {
			new File(this.archivePrayerDir).mkdir();
		}
		this.run();

	}
	public void run() {
		// get current and new file directories
		String activeFilename = String.format("%s/%s.avro", this.activePrayerDir, this.prayerId);
		String archiveFilename = String.format("%s/%s.avro", this.archivePrayerDir, this.prayerId);

		if (new File(activeFilename).exists()) {
			// generate success statement that ouputs prayer
			Avro avro = new Avro();
			List<GenericRecord> records = avro.getRecords(activeFilename, Prayer.class);
			GenericRecord prayer = records.get(0);

			// add answer to the prayer if applicable before archived
			// convert GenericRecord back to prayer, add resolution, save
			
			boolean isMoved = new File(activeFilename).renameTo(new File(archiveFilename));
			String output = String.format("Prayer [%s] Archived for [%s] Restored\n%s", prayer.get("prayerId"), prayer.get("entity"), prayer.get("content"));
			System.out.println(output);

		} else {
			String output = String.format("Prayer [%s] is not active or does not exist", this.prayerId);
			System.out.println(output);

		}
	}
}
