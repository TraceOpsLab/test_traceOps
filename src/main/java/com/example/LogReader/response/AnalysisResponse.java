package com.example.LogReader.response;


import com.example.LogReader.dto.AnalysisResultDTO;

import java.util.List;

public class AnalysisResponse {
    private String message;
    private List<AnalysisResultDTO> results;
    private String excelStatus;

    public AnalysisResponse(String message, List<AnalysisResultDTO> results, String excelStatus) {
        this.message = message;
        this.results = results;
        this.excelStatus = excelStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AnalysisResultDTO> getResults() {
        return results;
    }

    public void setResults(List<AnalysisResultDTO> results) {
        this.results = results;
    }

    public String getExcelStatus() {
        return excelStatus;
    }

    public void setExcelStatus(String excelStatus) {
        this.excelStatus = excelStatus;
    }
}
