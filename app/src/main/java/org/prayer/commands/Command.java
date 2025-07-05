package org.prayer;

public class Command {
	public String[] args;
	public String command;
	public String description;
	public Command(String[] args) {
		this.args = args;
		this.command = command;
	}
	public void setArgs() {};
	public void run() {
		System.out.println(this.getClass().getSimpleName());
	};
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCommand(String command) {
		this.command = command;
	}
}
