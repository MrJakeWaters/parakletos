package org.parakletos;

// java
import java.util.Arrays;

public class PrayerCommand extends Command {
	public String prayerDir;
	public PrayerCommand(String[] args) {
		super(args);
		this.prayerDir = String.format("%s/prayer-list", Init.PK_CONFIG_HOME).replaceAll("//","/");

		// execution
		if (this.function.equals("--add")) {
			AddPrayerSubCommand subCommand = new AddPrayerSubCommand(this.args, this.prayerDir);

		} else if (this.function.equals("--read")) {
			ReadPrayerListSubCommand subCommand = new ReadPrayerListSubCommand(this.args, this.prayerDir);
			subCommand.getPrayerContent().print();

		} else {
			System.out.println("No command specified");

		}
	}
}
