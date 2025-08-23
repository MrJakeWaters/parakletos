package org.parakletos;

// java
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public enum Online {
	INSTANCE;
	
	// private online status
	boolean status;

	// set internet connection status
	public boolean getStatus() {
		try {
		    // attempt to connect to a reliable website
		    URL url = new URL("http://www.google.com");
		    URLConnection connection = url.openConnection();
		    connection.connect(); // Attempt to connect to the resource
		    return true;

		} catch (MalformedURLException e) {
		    // This exception indicates an issue with the URL format, not necessarily connectivity
		    System.err.println("Invalid URL format: " + e.getMessage());
		    return false;

		} catch (IOException e) {
		    // This exception often indicates a network issue (e.g., no internet connection)
		    System.err.println("Error connecting to the internet: " + e.getMessage());
		    return false;

		}
	}
}
