package org.parakletos;

// java
import java.util.Arrays;

public class PrayerCommand extends Command {
	public String activePrayerDir;
	public String archivePrayerDir;
	public PrayerCommand(String[] args) {
		super(args);
		this.activePrayerDir = String.format("%s/prayer-list/active", Init.PK_CONFIG_HOME).replaceAll("//","/");
		this.archivePrayerDir = String.format("%s/prayer-list/archive", Init.PK_CONFIG_HOME).replaceAll("//","/");

		// execution
		if (this.function.equals("--add")) {
			AddPrayerSubCommand subCommand = new AddPrayerSubCommand(this.args, this.activePrayerDir);

		} else if (this.function.equals("--read")) {
			// set to active or archived prayer directory
			String prayerDir = this.activePrayerDir;
			try {
				if (this.args[0].equals("--archive")) {
					prayerDir = this.archivePrayerDir;	
				}

			} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				;	

			}

			// read prayers
			ReadPrayerListSubCommand subCommand = new ReadPrayerListSubCommand(this.args, prayerDir);
			subCommand.getPrayerContent().print();

		} else if (this.function.equals("--archive")) {
			ArchivePrayerSubCommand subCommand = new ArchivePrayerSubCommand(this.args, this.activePrayerDir, this.archivePrayerDir);

		} else if (this.function.equals("--restore")) {
			RestorePrayerSubCommand subCommand = new RestorePrayerSubCommand(this.args, this.activePrayerDir, this.archivePrayerDir);

		} else {
			System.out.println("No command specified");

		}
	}
}
