package com.example.LogReader.request;


import com.example.LogReader.response.Content;

import java.util.List;

// GeminiRequest.java
public class GeminiRequest {
    private List<Content> contents;

    public GeminiRequest(List<Content> contents) {
        this.contents = contents;
    }

    // Getters and setters
    public List<Content> getContents() { return contents; }
    public void setContents(List<Content> contents) { this.contents = contents; }
}

