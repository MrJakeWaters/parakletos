package org.parakletos;

// java
import java.util.Arrays;

public class CommandFactory {
	public Command command;
	public String function;
	public String[] args;
	public CommandFactory(String[] args) {
		this.function = args[0];
		this.args = Arrays.copyOfRange(args, 1, args.length);

		// execution
		if (this.function.equals("journal")) {
			this.command = new JournalCommand(this.args);		
		} else if (this.function.equals("bible")) {
			System.out.println("Coming soon...");
		}
	}
}
