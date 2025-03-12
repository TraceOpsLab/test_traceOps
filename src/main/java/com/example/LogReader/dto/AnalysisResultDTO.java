package com.example.LogReader.dto;

public class AnalysisResultDTO {
    private String errorMessage;
    private String serviceName;
    private String className;
    private int httpStatus;
    private int frequency;
    private String aiAnalysis;

    public AnalysisResultDTO(String errorMessage, String serviceName, String className, int httpStatus, int frequency, String aiAnalysis) {
        this.errorMessage = errorMessage;
        this.serviceName = serviceName;
        this.className = className;
        this.httpStatus = httpStatus;
        this.frequency = frequency;
        this.aiAnalysis = aiAnalysis;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getAiAnalysis() {
        return aiAnalysis;
    }

    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }
}
