package com.example.LogReader.response;

import java.util.List;

// GeminiResponse.java
public class GeminiResponse {
    private List<Candidate> candidates;

    public List<Candidate> getCandidates() { return candidates; }
    public void setCandidates(List<Candidate> candidates) { this.candidates = candidates; }
}
