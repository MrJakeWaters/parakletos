package org.parakletos;

// java
import java.util.Arrays;

public class Command {
	public String function;
	public SubCommand subCommand;
	public String[] args;
	public Command(String[] args) {
		this.function = args[0];
		this.args = Arrays.copyOfRange(args, 1, args.length);
		this.showBibleVerse();
	}
	public void showBibleVerse() {
		BibleSuperSearchApi bible = new BibleSuperSearchApi();
		String verseReference = bible.getRandomBibleVerseReference();
		String verse = bible.getBibleVerse(verseReference.replace(" ", "%20"));
		String output = String.format("%s[%s]%s %s", Formatting.BOLD_ON, verseReference, Formatting.BOLD_OFF, verse);
		System.out.println(output);
	}
}
