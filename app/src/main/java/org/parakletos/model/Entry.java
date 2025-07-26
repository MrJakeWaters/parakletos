package org.parakletos;

// java
import java.util.UUID;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
// avro
import org.apache.avro.reflect.Nullable;
import org.apache.avro.generic.GenericRecord;

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
	public Entry(GenericRecord record) { 
		this.entryId = String.valueOf(record.get("entryId"));
		this.prayerId = String.valueOf(record.get("prayerId"));
		this.entryStartTs = String.valueOf(record.get("entryStartTs"));
		this.entryEndTs = String.valueOf(record.get("entryEndTs"));
		this.text = String.valueOf(record.get("text"));
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

