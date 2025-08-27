package org.parakletos;

// java
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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

}
