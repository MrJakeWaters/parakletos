package org.prayer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Entry {
	private Instant entryTimestamp; 
	private String text;
	public Entry(String text) {
		this.text = text;
		this.entryTimestamp = Instant.now().truncatedTo(ChronoUnit.SECONDS);
	}
	public String getText() {
		return this.text;
	}
	public Instant getEntryTimestamp() {
		return this.entryTimestamp;
	}
}

