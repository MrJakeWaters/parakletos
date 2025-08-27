package org.parakletos;

// java
import java.util.Arrays;

public class Command {
	public String function;
	public String[] args;
	public String description;
	public Command(String[] args) {
		this.function = args[0];
		this.args = Arrays.copyOfRange(args, 1, args.length);
	
	}
}
