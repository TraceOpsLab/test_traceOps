package com.example.LogReader;

import com.example.LogReader.service.FileReaderService;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.logging.*;
import com.google.cloud.logging.Logging.EntryListOption;
import com.google.cloud.logging.LoggingOptions;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


//@SpringBootApplication
public class LogReaderApplication {

	/*public static void main(String[] args) {
		SpringApplication.run(LogReaderApplication.class, args);
	}*/

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
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
						System.out.println("No Logs Available");
					}else{
						System.out.printf("WOWOWOWOWOWOWOWO------------->  Timestamp: %s | Log: %s%n", entry.getTimestamp(), entry.getPayload().toString());
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
