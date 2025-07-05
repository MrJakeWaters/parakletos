package org.prayer;

public class App {
    public String start(String[] args) {
		Init init = new Init();
		System.out.println(args);
		System.out.println(init.getHeader());
		System.out.println(init.getBibleVerse());
		CommandFactory factory = new CommandFactory(args);
		return Init.EXIT_MESSAGE;
    }

    public static void main(String[] args) {
        System.out.println(new App().start(args));
    }
}
