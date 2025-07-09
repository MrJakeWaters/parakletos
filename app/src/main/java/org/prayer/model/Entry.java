package org.prayer;

import java.time.Instant;
import java.util.UUID;
import java.time.temporal.ChronoUnit;

public class Entry {
	public Instant entryTimestamp; 
	public String text;
	public String entryId;
	public Entry(String text) {
		this.text = text;
		this.entryTimestamp = Instant.now().truncatedTo(ChronoUnit.SECONDS);
		this.entryId = UUID.randomUUID().toString();
	}
	public String getText() {
		return this.text;
	}
	public Instant getEntryTimestamp() {
		return this.entryTimestamp;
	}
	public String getEntryId () {
		return this.entryId;	
	}
}

