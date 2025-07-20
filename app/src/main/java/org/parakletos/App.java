package org.parakletos;

// java
import java.util.logging.Logger;
import java.util.logging.LogManager;

public class App {
	public final static String WELCOME_MESSAGE = "Glory to God in the highest and peace to his people on earth";
	public final static String EXIT_MESSAGE = "\nAll Done!";
    public String start(String[] args) {
		System.out.println(App.WELCOME_MESSAGE);

		// disable logs
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);

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
