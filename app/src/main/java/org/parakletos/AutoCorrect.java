package org.parakletos;

// java
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
// JLanguageTool
import org.languagetool.Languages;
import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;

public class AutoCorrect {
	/* 
		uses JLanguage to find errors and produce correction suggestions
		meant to be a simple abstraction layer on top of JLang to encapsulate 
		correction logic
	*/
	public String content;
	public String correction;
	public String formattedContent;
	public String formattedCorrection;
	public String languageCode;
	public int standardReplacements;
	private int i = 1;
	private JLanguageTool lt;
	private List<RuleMatch> matches;
	// private

	public AutoCorrect(String content, String languageCode) {
		this.content = content;
		this.formattedContent = content;
		this.correction = content;
		this.formattedCorrection = content;
		this.languageCode = languageCode;
		this.lt = new JLanguageTool(Languages.getLanguageForShortCode(this.languageCode));
		try {
			this.matches = this.lt.check(this.content);
			this.standardReplacements = this.matches.size();
			for (RuleMatch match: this.matches) {
				this.standardMatchReplacement(match, this.i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setSpecificReplacements(String indices) {
		this.correction = this.content;
		this.i = 1;
		for (String index: indices.split(",")) {
			this.standardMatchReplacement(this.matches.get(Integer.parseInt(index)-1), i);
		}
	}
	public String getContent() {
		return this.content;
	}
	public String getCorrection() {
		return this.correction;
	}
	public String getLanguageCode() {
		return this.languageCode;
	}
	public String getFormattedCorrection() {
		return this.formattedCorrection;
	}
	public String getFormattedContent() {
		return this.formattedContent;
	}
	public int getStandardReplacements() {
		return this.standardReplacements;
	}
	public boolean hasErrors() {
		return !this.content.equals(this.correction);
	}
	public void standardMatchReplacement(RuleMatch match, int id) {
		try {
			// indexes
			int start = match.getFromPos();
			int end = match.getToPos();
		
			// get percieved error
			String error = this.content.substring(start, end);
			
			// suggestion
			String suggestion = match.getSuggestedReplacements().get(0);
			for (String suggest: match.getSuggestedReplacements()) {
				System.out.println(String.format("(%s) %s", this.i, suggest));
			}
		
			// replace suggestion
			this.correction = this.correction.replaceFirst(error, suggestion);
			this.formattedCorrection = this.formattedCorrection.replaceFirst(error, this.getFormatCorrection(suggestion, Formatting.ANSI_CYAN, id));
			this.formattedContent = this.formattedContent.replaceFirst(error, this.getFormatCorrection(error, Formatting.ANSI_RED));
			this.i += 1;
		} catch (java.lang.IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	public String getFormatCorrection(String match, String color) {
		String formatOn = Formatting.BOLD_ON + color;
		String formatOff = Formatting.BOLD_OFF + Formatting.ANSI_WHITE;
		String replacement = String.format("%s%s%s", formatOn, match, formatOff);
		return replacement;
	}
	public String getFormatCorrection(String match, String color, int id) {
		String idFormat = String.format("%s(%s%s%s)%s", Formatting.ANSI_YELLOW, Formatting.ANSI_WHITE, id, Formatting.ANSI_YELLOW, Formatting.ANSI_WHITE);
		String formatOn = Formatting.BOLD_ON + color;
		String formatOff = Formatting.BOLD_OFF + Formatting.ANSI_WHITE;
		String replacement = String.format("%s%s%s%s", idFormat, formatOn, match, formatOff);
		return replacement;
	}
}
