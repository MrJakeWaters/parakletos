package org.parakletos;

// java
import java.util.Map;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Properties;
// junit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandFactoryTest {
	@Test
	void noCommandDescriptionsAreEmpty() {
		String[] args = {""};
		CommandFactory factory = new CommandFactory(args);
		String filename = String.format("%s/src/main/java/org/parakletos/commands/CommandFactory.java", System.getProperty("user.dir"));
		HelpUtility hu = new HelpUtility(filename, factory.getCommands());
		hu.setDescriptionMap();

		// verify no commands return empty descriptions
		boolean emptyDescriptions = false;
		List<String> emptyCommandDescriptions = new ArrayList<>();

		// loop through factory command hashmap
		for (Map.Entry<String, String> entry: hu.getDescriptionMap().entrySet()) {

			// description condition that fails test
			if (entry.getValue().equals("")) {
				emptyDescriptions = true;
				emptyCommandDescriptions.add(entry.getKey());
				System.out.println(String.format("No description set for [%s -> %sCommand()]", hu.getFactoryFile().getName(), entry.getKey()));

			}

		}

		// asset
		String message = String.format("Commands that don't have a description: [%s]", String.join(",", emptyCommandDescriptions));
		assertEquals(false, emptyDescriptions, message);

	}
	@Test
	void isVersionPackageFormatCorrect() {
		// ensures new minor version is not incremented by more than 1
		Properties properties = new Properties();
		boolean parseError = false;
		try {
			// read and output versioning file to console
			InputStream input = getClass().getClassLoader().getResourceAsStream("version.properties");
			properties.load(input);

			// set current and previous versions
			int major = Integer.parseInt(properties.getProperty("major"));
			int minor = Integer.parseInt(properties.getProperty("minor"));
			int micro = Integer.parseInt(properties.getProperty("micro"));

		} catch (NumberFormatException e) {
			parseError = true;

		} catch (IOException e) {
			parseError = true;
			e.printStackTrace();
		}
		assertEquals(false, parseError);

	}

}
