package org.parakletos;

// java
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

public class PrayerCommand extends Command {
	public String activePrayerDir;
	public String archivePrayerDir;
	public Map<String, Runnable> commands = new HashMap<>();
	public PrayerCommand(String[] args) {
		super(args);
		this.activePrayerDir = String.format("%s/prayer-list/active", Init.PK_CONFIG_HOME).replaceAll("//","/");
		this.archivePrayerDir = String.format("%s/prayer-list/archive", Init.PK_CONFIG_HOME).replaceAll("//","/");

		// functional hash map
		this.commands.put("add", () -> this.addCommand());
		this.commands.put("read", () -> this.readCommand());
		this.commands.put("archive", () -> this.archiveCommand());
		this.commands.put("restore", () -> this.restoreCommand());
		this.commands.put("help", () -> this.helpCommand());

	}
	public Map<String, Runnable> getCommands() {
		return this.commands;

	}
	public void execute() {
		// execute cli command
		if (this.commands.containsKey(this.function)) {
			this.commands.get(this.function).run();

		} else {
			System.out.println(String.format("The following command does not exist [%s]", this.function));
	
		}

	}
	public void addCommand() {
		// add a prayer to your prayer list
		AddPrayerSubCommand subCommand = new AddPrayerSubCommand(this.args, this.activePrayerDir);

	}
	public void readCommand() {
		// read prayers in your prayer list (archived or active)
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

	}
	public void archiveCommand() {
		// archive a prayer from your prayer list
		ArchivePrayerSubCommand subCommand = new ArchivePrayerSubCommand(this.args, this.activePrayerDir, this.archivePrayerDir);

	}
	public void restoreCommand() {
		// restore an archived prayer
		RestorePrayerSubCommand subCommand = new RestorePrayerSubCommand(this.args, this.activePrayerDir, this.archivePrayerDir);

	}
	public void helpCommand() {
		// displays all commands options
		String filename = String.format("%s/src/main/java/org/parakletos/commands/PrayerCommand.java", System.getProperty("user.dir"));
		HelpUtility hu = new HelpUtility(filename, this.commands);
		hu.setDescriptionMap();

		// display
		hu.display();

	}
}
