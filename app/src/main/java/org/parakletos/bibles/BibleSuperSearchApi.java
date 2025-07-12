package org.parakletos;

// java
import java.net.URI;
import java.util.Map;
import java.io.IOException;
import java.util.Collections;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.lang.InterruptedException;
import java.net.http.HttpResponse.BodyHandlers;
// jackson
import com.fasterxml.jackson.databind.ObjectMapper;

public class BibleSuperSearchApi {
	public static String BOOKS_URL = "https://api.biblesupersearch.com/api/books";
	public HttpClient client = HttpClient.newHttpClient();
	public ObjectMapper mapper = new ObjectMapper();
	public BibleSuperSearchApi() {}

	public Map<String, Object> getBibleBooks() {
		try {
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BibleSuperSearchApi.BOOKS_URL))
				.build();

			// return client
			String responseBody = client.send(request, BodyHandlers.ofString()).body();
			return mapper.readValue(responseBody, Map.class);
		} catch (IOException e) {
			
			// handling exception
			e.printStackTrace();
			return Collections.emptyMap();
		} catch (InterruptedException e) {

			// handling exception
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}
}
