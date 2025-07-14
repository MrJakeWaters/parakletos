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
	public static String BOOKS_URL = "https://api.biblesupersearch.com/api/books";
	public static String REFERENCE_URL = "https://api.biblesupersearch.com/api?bible=";
	public HttpClient client = HttpClient.newHttpClient();
	public ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	public BibleSuperSearchApi() {}

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
	public String getBibleVerse(String bibleVerseReference) {
		try {
			Map<String, String> configs = mapper.readValue(new File(Init.PK_CONFIG), Map.class);
			String bibleVersion = configs.get("bibleVersion");
			String url = String.format("%s%s&reference=%s", BibleSuperSearchApi.REFERENCE_URL, bibleVersion, bibleVerseReference);
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();
			String responseBody = client.send(request, BodyHandlers.ofString()).body();
			JsonNode root = mapper.readTree(responseBody);
			String chapter = root.path("results").get(0).path("chapter_verse").asText().split(":")[0];
			String verse = root.path("results").get(0).path("chapter_verse").asText().split(":")[1];
			return root.path("results").get(0).path("verses").path(bibleVersion).path(chapter).path(verse).path("text").asText();
		} catch (IOException e) {
			
			// handling exception
			System.out.println(bibleVerseReference);
			e.printStackTrace();
			return "";
		} catch (InterruptedException e) {

			// handling exception
			System.out.println(bibleVerseReference);
			e.printStackTrace();
			return "";
		}
	}
}
