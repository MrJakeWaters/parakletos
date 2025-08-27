package org.parakletos;

// java
import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.lang.InterruptedException;
import java.net.http.HttpResponse.BodyHandlers;
// jackson
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

public class BibleSuperSearchApi {
	public static String BIBLES_URL = "https://api.biblesupersearch.com/api/bibles";
	public static String REFERENCE_URL = "https://api.biblesupersearch.com/api?bible=";
	public HttpClient client = HttpClient.newHttpClient();
	public ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	public BibleSuperSearchApi() {}

	public String getRandomBibleMessage() {
		try {
			String bibleBooksJson = new String(getClass().getClassLoader().getResourceAsStream("bibleBooks.json").readAllBytes());
			BibleSuperBooks books = mapper.readValue(bibleBooksJson, BibleSuperBooks.class);
			BibleSuperBook randomBook = books.getRandomBook();
			int chapter = randomBook.getRandomChapter();
			int startingVerse = randomBook.getRandomVerse(chapter);
			int endingVerse = randomBook.getRandomVerse(chapter, startingVerse);
			String output = String.format("%s %s:%s-%s", randomBook.getShortname(), chapter, startingVerse, endingVerse);
			return output;

		} catch (IOException e) {
			// handling exception
			e.printStackTrace();
			return "";

		}
	}

	public String getRandomBibleVerseReference() {
		try {
			// return client
			String bibleBooksJson = new String(getClass().getClassLoader().getResourceAsStream("bibleBooks.json").readAllBytes());
			BibleSuperBooks books = mapper.readValue(bibleBooksJson, BibleSuperBooks.class);
			BibleSuperBook randomBook = books.getRandomBook();
			int chapter = randomBook.getRandomChapter();
			int verse = randomBook.getRandomVerse(chapter);
			String output = String.format("%s %s:%s", randomBook.getShortname(), chapter, verse);
			return output;

		} catch (IOException e) {
			// handling exception
			e.printStackTrace();
			return "";

		}
	}

	public String getBibleBookChapterReference(String bookName, int chapter) {
			try {
				String bibleBooksJson = new String(getClass().getClassLoader().getResourceAsStream("bibleBooks.json").readAllBytes());
				BibleSuperBooks books = mapper.readValue(bibleBooksJson, BibleSuperBooks.class);
				// get specific book
				BibleSuperBook specificBook = books.getBook(bookName);
				return specificBook.getFullChapterReference(chapter);

			} catch (IOException e) {
				e.printStackTrace();	
				return "";

			}

	}

	public String getRandomBibleChapterReference() {
		try {
			// return client
			String bibleBooksJson = new String(getClass().getClassLoader().getResourceAsStream("bibleBooks.json").readAllBytes());
			BibleSuperBooks books = mapper.readValue(bibleBooksJson, BibleSuperBooks.class);
			// get random book
			BibleSuperBook randomBook = books.getRandomBook();
			return randomBook.getRandomFullChapterReference();

		} catch (IOException e) {
			// handling exception
			e.printStackTrace();
			return "";

		}
	}

	public String getBibleVerse(String bibleVerseReference) {
		// taking reference to bible verse(s) and return the actual verse content
		try {
			Map<String, String> configs = mapper.readValue(new File(Init.PK_CONFIG), Map.class);
			String bibleVersion = configs.get("bibleVersion");
			String url = String.format("%s%s&reference=%s", BibleSuperSearchApi.REFERENCE_URL, bibleVersion, bibleVerseReference);
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
			String responseBody = client.send(request, BodyHandlers.ofString()).body();
			JsonNode root = mapper.readTree(responseBody);
			String book = root.path("results").get(0).path("book_short").asText();
			String chapter = root.path("results").get(0).path("chapter_verse").asText().split(":")[0];
			String output = String.format("%s[%s %s] %s%s\n", Formatting.BOLD_ON, book, chapter, bibleVersion.toUpperCase(), Formatting.BOLD_OFF);
			for (JsonNode index: root.path("results").get(0).path("verse_index").path(chapter)) {
				String i = index.toString();
				String verse = root.path("results").get(0).path("verses").path(bibleVersion).path(chapter).path(i).path("text").asText();
				output = String.format("%s%s(%s%s%s%s%s)%s %s", output, Formatting.YELLOW, Formatting.WHITE, Formatting.BOLD_ON, i, Formatting.BOLD_OFF, Formatting.YELLOW, Formatting.WHITE, verse);

			}
			return output;

		} catch (IOException e) {
			// handling exception
			System.out.println(String.format("You're likely not connected to the internet", bibleVerseReference));
			return "";

		} catch (InterruptedException e) {
			// handling exception
			System.out.println(String.format("InterruptedException when getting [%s]", bibleVerseReference));
			return "";

		}
	}
	
	public JsonNode getBibles() {
		try {
			// get bible object and fields
			
			// retrieve and parse all bible version names that exist
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BIBLES_URL))
				.build();
			String responseBody = client.send(request, BodyHandlers.ofString()).body();
			return mapper.readTree(responseBody).path("results");

		} catch (IOException e) {
			// handling exception
			return mapper.createObjectNode();

		} catch (InterruptedException e) {
			// handling exception
			return mapper.createObjectNode();

		}
	}
}
