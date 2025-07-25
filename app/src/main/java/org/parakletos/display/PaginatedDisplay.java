package org.parakletos;

// java
import java.util.ArrayList;
// system hooks
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
// apache common
// import org.apache.commons.lang3.text.WordUtils;

public class PaginatedDisplay {
	// https://github.com/akullpp/awesome-java#cli
	public String content;
	public int displayedLines;
	public int maxColumns;
	public int maxLines;
	public PaginatedDisplay(String content) { 
		this.content = content;
		this.displayedLines = 0;
	}
	public void addContent(String line) {
		this.content = String.format("%s%s", this.content, line);
	}
	public void print() {
		System.out.println(this.content);
	}
}
