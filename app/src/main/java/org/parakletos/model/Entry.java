package org.parakletos;

// java
import java.util.UUID;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
// avro
import org.apache.avro.reflect.Nullable;

public class Entry {
	public String entryStartTs; 
	@Nullable
	public String prayerId;
	public String entryEndTs; 
	public String text;
	public String entryId;

	// constructor
	public Entry() {
		this.entryId = UUID.randomUUID().toString();
		this.entryStartTs = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("T"," ").replace("Z","");
		this.prayerId = null;
	}

	// getters
	public String getPrayerId() {
		return this.prayerId;
	}
	public String getText() {
		return this.text;
	}
	public String getEntryStartTs() {
		return this.entryStartTs;
	}
	public String getEntryEndTs() {
		return this.entryEndTs;
	}
	public String getEntryId () {
		return this.entryId;	
	}

	// setters
	public void setEntryEndTs() {
		this.entryEndTs = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("T"," ").replace("Z","");
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setPrayerId(String prayerId) {
		this.prayerId = prayerId;
	}
}

