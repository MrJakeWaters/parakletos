package org.prayer; 

import java.io.Console;
import java.util.Scanner;

public class Workflow {
	public Scanner scanner;
	public Workflow() {
		this.scanner = new Scanner(System.in);
	}
	public String getUserInput(String prompt) {
		String value = System.console().readLine(prompt);
		return value;
	}
	public String getUserInputPassword(String prompt) {
		char[] input = System.console().readPassword(prompt);
		String password = "";
		for (char c: input) {
			password = String.format("%s%s", password, String.valueOf(c));
		}
		return password;
	}
	public boolean getUserInputBoolean(String prompt) {
		System.out.println(prompt);
		String value = String.valueOf(this.scanner.nextLine());

		// ensure they enter [y/n]
		while(!value.equals("Y") && !value.equals("y") && !value.equals("N") && !value.equals("n")) {
			System.out.println("Enter [y|n]");
			value = String.valueOf(this.scanner.nextLine());
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
		String value2 = this.getUserInputPassword("Enter once more to make sure: ");
		while (!value1.equals(value2)) {
			value1 = this.getUserInputPassword("Oops, those didn't quite mache up. Try again: ");
			value2 = this.getUserInputPassword("Enter once more to make sure: ");
		}
		return value1;
	}
	public String getUserInputWithValidation(String prompt) {
		String value1 = this.getUserInput(prompt);
		String value2 = this.getUserInput("Enter once more to make sure");
		while (!value1.equals(value2)) {
			value1 = this.getUserInput("Oops, those didn't quite mache up. Try again...");
			value2 = this.getUserInput("Enter once more to make sure");
		}
		return value1;
	}
	public void closeScanner() {
		this.scanner.close();
	}
}
