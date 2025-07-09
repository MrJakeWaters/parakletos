package org.prayer;

import java.time.Instant;
import java.util.UUID;
import java.time.temporal.ChronoUnit;

public class Entry {
	public String entryTimestamp; 
	public String text;
	public String entryId;
	public Entry(String text) {
		this.text = text;
		this.entryTimestamp = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("T"," ").replace("Z","");
		this.entryId = UUID.randomUUID().toString();
	}
	public String getText() {
		return this.text;
	}
	public String getEntryTimestamp() {
		return this.entryTimestamp;
	}
	public String getEntryId () {
		return this.entryId;	
	}
}

