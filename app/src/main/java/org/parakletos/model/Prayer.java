package org.parakletos;

// java
import java.util.UUID;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Prayer {
	public String prayerTs; 
	public String prayerId;
	public String content;
	public String entity;
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
