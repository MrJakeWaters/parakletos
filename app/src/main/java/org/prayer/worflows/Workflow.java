package org.prayer; 

import java.io.IOException;
import org.jline.reader.*;
import java.nio.file.Paths;
import org.jline.reader.Completer;
import org.jline.builtins.Completers.DirectoriesCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.*;
import org.jline.terminal.*;
import org.jline.terminal.impl.*;
import java.io.Console;
import java.util.Scanner;

public class Workflow {
	public Terminal terminal;
	public LineReader reader;
	public Workflow() {
		try {
			Completer dirCompleter = new DirectoriesCompleter(Paths.get("."));
			this.terminal = TerminalBuilder.builder().system(true).build();
			this.reader = LineReaderBuilder
				.builder()
				.terminal(this.terminal)
				.completer(dirCompleter)
				.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getUserInput(String prompt) {
		String value = this.reader.readLine(prompt);
		return value;
	}
	public String getUserInputPassword(String prompt) {
		String password = this.reader.readLine("*");
		return password;
	}
	public boolean getUserInputBoolean(String prompt) {
		String value = getUserInput(prompt);

		// ensure they enter [y/n]
		while(!value.equals("Y") && !value.equals("y") && !value.equals("N") && !value.equals("n")) {
			value = getUserInput("Enter [Y|n] ");
		}

		// convert output to boolean
		boolean output = false;
		if (value.equals("Y") || value.equals("y")) {
			output = true;
		} 
		else if (value.equals("N") || value.equals("n")) {
			output = false;
		}
		return output;
	}
	public String getUserInputPasswordWithValidation(String prompt) {
		String value1 = this.getUserInputPassword(prompt);
		String value2 = this.getUserInputPassword("Enter once more to make sure > ");
		while (!value1.equals(value2)) {
			value1 = this.getUserInputPassword("Oops, those didn't quite mache up. Try again > ");
			value2 = this.getUserInputPassword("Enter once more to make sure > ");
		}
		return value1;
	}
	public String getUserInputWithValidation(String prompt) {
		String value1 = this.getUserInput(prompt);
		String value2 = this.getUserInput("Enter once more to make sure > ");
		while (!value1.equals(value2)) {
			value1 = this.getUserInput("Oops, those didn't quite mache up. Try again > ");
			value2 = this.getUserInput("Enter once more to make sure > ");
		}
		return value1;
	}
}
