package com.example.LogReader;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogReaderApplication.class, args);
    }

    /*public static void main(String[] args) {
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

                // Define filter with timestamp constraints
//                String filter = String.format(
//                        "resource.type=\"cloud_function\" AND " +
//                                "resource.labels.function_name=\"%s\" AND " +
                              //  "timestamp >= \"%s\" AND timestamp <= \"%s\"",
//                        cloudFunctionName, startTime, endTime
//                );

				String filter = "resource.type = \"cloud_run_revision\"\n" +
						"resource.labels.service_name = \"demo-function-1\"\n" +
						"resource.labels.location = \"us-central1\"\n" +
						" severity>=DEFAULT";

                System.out.println("Fetching logs from: " + startTime + " to " + endTime);

                // Fetch log entries
                for (LogEntry entry : logging.listLogEntries(Logging.EntryListOption.filter(filter)).iterateAll()) {
                    System.out.printf("Timestamp: %s | Log: %s%n", entry.getTimestamp(), entry.getPayload().toString());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading credentials file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error fetching logs: " + e.getMessage());
        }
    }*/
}

