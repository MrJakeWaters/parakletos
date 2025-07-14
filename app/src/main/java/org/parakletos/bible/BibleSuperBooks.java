package org.parakletos;

// java
import java.util.List;
import java.util.Random;

public class BibleSuperBooks {
	public List<String> errors;
	public List<BibleSuperBook> results;
	public List<String> getError() {
		return this.errors;
	}
	public List<BibleSuperBook> getResults() {
		return this.results;
	}
	public BibleSuperBook getRandomBook() {
		int randomIndex = (new Random()).nextInt(this.results.size()-1);
		return this.results.get(randomIndex);
	}
}
