package org.parakletos;

// java
import java.util.UUID;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
// avro
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.Nullable;

public class Prayer {
	public String prayerTs; 
	public String prayerId;
	public String content;
	@Nullable
	public String resolution;
	public String entity;
	public Prayer(GenericRecord record) {
		this.prayerId = String.valueOf(record.get("prayerId"));
		this.prayerTs = String.valueOf(record.get("prayerTs"));
		this.entity = String.valueOf(record.get("entity"));
		this.content = String.valueOf(record.get("content"));

	}
	public Prayer(String entity, String content) {
		this.prayerId = UUID.randomUUID().toString();
		this.prayerTs = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("T"," ").replace("Z","");
		this.entity = entity;
		this.content = content;

	}
	public Prayer() {
		this.prayerId = UUID.randomUUID().toString();
		this.prayerTs = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("T"," ").replace("Z","");

	}

	// setters
	public void setContent(String content) {
		this.content = content;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	// getters
	public String getPrayerTs() {
		return this.prayerTs;
	}
	public String getContent() {
		return this.content;
	}
	public String getEntity() {
		return this.entity;
	}
	public String getPrayerId() {
		return this.prayerId;
	}
}
