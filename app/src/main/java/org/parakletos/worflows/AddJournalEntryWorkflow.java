package org.parakletos;

// java
import java.lang.NumberFormatException;

public class AddJournalEntryWorkflow {
	public Entry entry;
	public AutoCorrect suggestions;
	private Workflow workflow = new Workflow();

	public AddJournalEntryWorkflow(Entry entry) {
		this.entry = entry;

		// spelling and grammer validations for entries
		this.suggestions = new AutoCorrect(this.entry.getText(), "en-US");
		
		if (suggestions.hasErrors()) {
			String errorPrompt = String.format("\nI've got a few suggestions for ya?\n\n --> %s\n --> %s\n\n", suggestions.getFormattedContent(), suggestions.getFormattedCorrection());
			System.out.println(errorPrompt);
			boolean useSuggestions = workflow.getUserInputBoolean("Do you want to make any corrections? [Y/n] ");
	
			// determine what the user wants to replace
			if (useSuggestions) {
				String corrections = workflow.getUserInput("Hit [Enter] to accept all the suggestions and publish as is, otherwise add comma separated list of numbers for which suggestions you'd like to accept: ");
				
				// ensure you get a valid input
				while (!this.isValidCorrection(corrections)) {
					corrections = workflow.getUserInput("Try again: ");
				}

				// replace suggestions
				if (!corrections.equals("")) {
					suggestions.setSpecificReplacements(corrections);
					
				}
				// update entry with suggestions
				this.entry.setText(suggestions.getCorrection());
			}
			
			// Display and update entry
			System.out.println(String.format("\n%sCommitting Entry%s: %s", Formatting.BOLD_ON, Formatting.BOLD_OFF, this.entry.getText()));
		}
	}
	public boolean isValidCorrection(String input) {
		boolean result = true;
		int x;
		// empyt is a valid entry
		if (!input.equals("")) {
			for (String val: input.split(",")) {
				// validate it's numeric
				try {
					x = Integer.parseInt(val);
				} catch (NumberFormatException e) {
					x = -1;
					System.out.println(String.format("%s, could not be parse", val));
					result = false;
				}
				// validate it's not over indexed
				if (x >= this.suggestions.getStandardReplacements()) {
					System.out.println(String.format("All values need to be lower than the number of suggested matches [%s]", this.suggestions.getStandardReplacements()));
					result = false;
				}
			}
		}
		return result;
	}
}
