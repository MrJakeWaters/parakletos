package org.prayer;
import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitTest {
	@Test
	public void appDirectoryConfigisDirectory() {
		Init init = new Init();
		File directory = new File(Init.PRAYER_CONFIG_HOME);
		assertEquals(true, directory.isDirectory());
	}
}
