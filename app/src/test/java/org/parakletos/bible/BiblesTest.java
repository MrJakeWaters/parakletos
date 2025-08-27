package org.parakletos;

// junit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// java
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;
// jackson
import com.fasterxml.jackson.databind.JsonNode;

public class BiblesTest {
	@Test
	void DoAllBibleVersionOptionsExist() {
		// ensures that all bible versions that are displayed through the "bible set-version" command exist
		BibleSuperSearchApi api = new BibleSuperSearchApi();
		JsonNode bibles = api.getBibles();

		// bible version options
		boolean pass = true;
		List<String> versionsThatDoNotExist = new ArrayList<>();
		for (Field field: new Bibles().getClass().getDeclaredFields()) {
			field.setAccessible(true);
			
			// check if the name exists in the response for all bibles
			//System.out.println(bibles.path(field.getName()));
			String bibleVersion = field.getName().toLowerCase();
			if (!bibles.has(bibleVersion)) {
				pass = false;
				versionsThatDoNotExist.add(bibleVersion);
			}
		}
		
		// assert and create message for values that don't exist if applicable
		String message = String.format("The following Bible version don't exist: [%s]", String.join(",", versionsThatDoNotExist));
		assertEquals(true, pass, message);
	}
}
