package com.example.LogReader.converter;


import com.example.LogReader.dto.FilterDTO;

// FilterConverter.java
public class FilterConverter {
    public static String toGcpFilter(FilterDTO filter) {
        StringBuilder sb = new StringBuilder();

        if (filter.getLogName() != null)
            sb.append("logName = \"").append(filter.getLogName()).append("\" \n");
        if (filter.getSeverity() != null)
            sb.append("severity >= ").append(filter.getSeverity()).append(" \n");
        if (filter.getStartTime() != null && filter.getEndTime() != null)
            sb.append("timestamp >= \"").append(filter.getStartTime()).append("\" ")
                    .append("timestamp <= \"").append(filter.getEndTime()).append("\" ");
        if (filter.getHttpStatusCode() != null)
            sb.append("jsonPayload.statusCode = ").append(filter.getHttpStatusCode()).append(" ");

        // Handle labels (e.g., resource.labels.service_id="my-service")
        if (filter.getLabels() != null) {
            filter.getLabels().forEach((k, v) ->
                    sb.append("resource.labels.").append(k).append(" = \"").append(v).append("\" \n"));
        }

        return sb.toString().trim();
    }
}