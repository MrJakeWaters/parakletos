package org.parakletos;

// java
import java.io.File;
import java.util.Map;
import java.lang.Math;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.ArrayList;
import java.io.FileNotFoundException;
// java parser
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.body.MethodDeclaration;

public class HelpUtility {
	public int maxCommandLength = 0;
	public File factoryFile;
	public Map<String, Runnable> commandMap;
	public Map<String, String> descriptionMap = new HashMap<>();
	public CompilationUnit cu;
	public HelpUtility(String filename, Map<String, Runnable> commandMap) {
		this.factoryFile = new File(filename);
		this.commandMap = commandMap;

		// set max command length
		for (String key: this.commandMap.keySet()) {
			this.maxCommandLength = Math.max(maxCommandLength, key.length());

		}
		this.maxCommandLength = (int) (Math.ceil(maxCommandLength/10.0)*10.0); // round up to the nearest 10

		// set parser
		try {
			this.cu = StaticJavaParser.parse(this.factoryFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

	}
	public File getFactoryFile() {
		return this.factoryFile;

	}
	public void display() {
		System.out.println("Command List:");
		for (Map.Entry<String, String> entry: this.descriptionMap.entrySet()) {
			int numberOfSpaces = entry.getKey().length() - this.maxCommandLength;
			String spaces = String.format("%" + numberOfSpaces + "s", "");
			System.out.println(String.format("  %s%s%s", entry.getKey(), spaces, entry.getValue()));

		}

	}
	public Map<String, String> getDescriptionMap() {
		return this.descriptionMap;

	}
	public String camelCaseConverter(String command) {
		// converts dashed string to camelCase
		// i.e. random-verse --> randomVerse
		// i.e. random-chapter --> randomChapter
		String output = "";
		for (String str: command.split("-")) {
			output = String.format("%s%s%s", output, str.substring(0, 1).toUpperCase(), str.substring(1));

		}
		return output.substring(0, 1).toLowerCase() + output.substring(1);

	}
	public void setDescriptionMap() {
		for (String key: this.commandMap.keySet()) {
			String methodName = String.format("%sCommand", this.camelCaseConverter(key));
			this.descriptionMap.put(key, this.getDescription(methodName));

		}

	}
	public String getDescription(String method) {
		// returns body of whatever method is passed for the current class
		List<Comment> comments = this.cu.findFirst(MethodDeclaration.class, md -> md.getNameAsString().equals(method)).get().getAllContainedComments(); 

		// ensure something is passed even if method has no comments
		// we never want this to be the case but validation will happen through unit test
		if (comments.size() > 0) {
			return String.valueOf(comments.get(0)).replace("// ","").replaceAll("\n","");

		} else {
			return "";

		}

	}
	
}
