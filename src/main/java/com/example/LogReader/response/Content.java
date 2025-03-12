package com.example.LogReader.response;


import java.util.List;

// Content.java
public class Content {
    private List<Part> parts;

    public Content(List<Part> parts) {
        this.parts = parts;
    }

    // Getters and setters
    public List<Part> getParts() { return parts; }
    public void setParts(List<Part> parts) { this.parts = parts; }
}
