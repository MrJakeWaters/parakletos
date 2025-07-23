package org.parakletos;

// java
import java.util.Date;
import java.time.Instant;
import java.util.TimeZone;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
// avro
import org.apache.avro.reflect.Nullable;
import org.apache.avro.generic.GenericRecord;

public class DailyBibleChapter {
	public String refreshTs; 
	public String refreshDate;
	public String book;
	public int chapter;
	public String text;
	@Nullable
	public String historicalContext;
	public DailyBibleChapter(GenericRecord record) {
		this.refreshTs = String.valueOf(record.get("refreshTs"));
		this.book = String.valueOf(record.get("book"));
		this.chapter = Integer.parseInt(String.valueOf(record.get("chapter")));
		this.text = String.valueOf(record.get("text"));
		this.historicalContext = String.valueOf(record.get("historicalContext"));
	}
	public DailyBibleChapter() {
		this.refreshTs = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("T"," ").replace("Z","");
		this.setRefreshDate();
	}
	public DailyBibleChapter(String book, int chapter, String text) {
		this.book = book;
		this.chapter = chapter;
		this.text = text;
		this.refreshTs = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString().replace("T"," ").replace("Z","");
		this.refreshDate = this.getRefreshDate();
		this.historicalContext = null;
	}

	// getters
	public String getRefreshTs() {
		return this.refreshTs;
	}
	public String getText() {
		return this.text;
	}
	public String getHistoricalContext() {
		return this.historicalContext;
	}
	public String getRefreshDate() {
		return this.refreshDate;	
	}
	public String getReference() {
		return String.format("%s %s", this.book, this.chapter);
	}

	// setters
	public void setRefreshDate() {
		try {
			// get parse format and set to UTC timezone
			SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parseFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			// convert to Local time with specific format
			SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date d = parseFormat.parse(this.refreshTs);
			this.refreshDate = String.valueOf(displayFormat.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
			this.refreshDate = "";
		}
	}
	public void setBook(String book) {
		this.book = book;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
	public void setHistoricalContext(String historicalContext) {
		this.historicalContext = historicalContext;
	}
}
