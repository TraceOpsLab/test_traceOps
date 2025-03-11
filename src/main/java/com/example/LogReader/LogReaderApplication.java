package com.example.LogReader;

import com.example.LogReader.service.FileReaderService;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


//@SpringBootApplication
public class LogReaderApplication {

	public static void main(String[] args) {
		try {
			// Load credentials from classpath
			InputStream credentialsStream = Objects.requireNonNull(LogReaderApplication.class.getClassLoader().getResourceAsStream("static/smart-pattern-453207-h4-f2ebaf8fe6ab.json"),
					"Service account file not found in resources");

			LoggingOptions options = LoggingOptions.newBuilder()
					.setCredentials(ServiceAccountCredentials.fromStream(credentialsStream))
					.build();
			// Initialize logging client
			try (Logging logging = options.getService()) {
				String cloudFunctionName = "demo-function-1";

				// Define the time range (last 1 hour)
				Instant now = Instant.now();
				Instant oneHourAgo = now.minusSeconds(3600);

				// Convert to RFC3339 format
				String startTime = DateTimeFormatter.ISO_INSTANT.format(oneHourAgo);
				String endTime = DateTimeFormatter.ISO_INSTANT.format(now);

				FileReaderService service = new FileReaderService();
				String filter = service.readFile("filter.txt");

				System.out.println("Fetching logs from: " + startTime + " to " + endTime);

				// Fetch log entries
				for (LogEntry entry : logging.listLogEntries(Logging.EntryListOption.filter(filter)).iterateAll()) {
					if(Objects.isNull(entry.getPayload())){
						System.out.printf("No Logs Available");
					}else{
						System.out.printf("Timestamp: %s | Log: %s%n", entry.getTimestamp(), entry.getPayload().toString());
					}
				}
			}

		} catch (IOException e) {
			System.err.println("Error reading credentials file: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error fetching logs: " + e.getMessage());
		}
	}

}
