package org.prayer;

public class App {
	public final static String WELCOME_MESSAGE = "Welcome to pj, a cli journaling application that help you organize and express your thought and prayers to Jesus.";
	public final static String EXIT_MESSAGE = "\nAll Done!";
    public String start(String[] args) {
		System.out.println(App.WELCOME_MESSAGE);
		// ensure configurations and setup and displays header and bible verse
		Init init = new Init();

		// run commands
		CommandFactory factory = new CommandFactory(args);
		return App.EXIT_MESSAGE;
    }

    public static void main(String[] args) {
        System.out.println(new App().start(args));
    }
}
