package com.example.LogReader.response;


import com.fasterxml.jackson.annotation.JsonProperty;

// Candidate.java
public class Candidate {
    private Content content;
    private String finishReason;

    @JsonProperty("content")
    public Content getContent() { return content; }

    @JsonProperty("finishReason")
    public String getFinishReason() { return finishReason; }

    // Setters
    public void setContent(Content content) { this.content = content; }
    public void setFinishReason(String finishReason) { this.finishReason = finishReason; }
}