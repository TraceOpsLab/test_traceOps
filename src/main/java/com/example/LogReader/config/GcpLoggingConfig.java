package com.example.LogReader.config;

import com.example.LogReader.LogReaderApplication;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Configuration
public class GcpLoggingConfig {

    @Value("${spring.cloud.gcp.project-id:smart-pattern-453207-h4}")
    private String projectId;


    @Bean
    public Logging logging() throws IOException {
        // Load credentials from classpath
        InputStream credentialsStream = Objects.requireNonNull(LogReaderApplication.class.getClassLoader().getResourceAsStream("static/smart-pattern-453207-h4-5aa3a8fec882.json"),
                "Service account file not found in resources");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        return LoggingOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}