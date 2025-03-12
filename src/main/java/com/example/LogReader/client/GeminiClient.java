package com.example.LogReader.client;

import com.example.LogReader.request.GeminiRequest;
import com.example.LogReader.response.Content;
import com.example.LogReader.response.GeminiResponse;
import com.example.LogReader.response.Part;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class GeminiClient {
    @Value("${gemini.api.key:AIzaSyBRFUCxmaR1Wwt-eT0NY_4p_doq1ApfiCw}")
    private String apiKey;

    public String analyzeError(String errorMessage) {
        WebClient client = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        GeminiRequest request = new GeminiRequest(List.of(new Content(List.of(new Part(errorMessage)))));

        return client.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", apiKey)
                        .build())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .map(response -> response.getCandidates().get(0).getContent().getParts().get(0).getText())
                .block();
    }

}