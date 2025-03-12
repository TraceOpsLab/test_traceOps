package com.example.LogReader.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {
    private String logName;
    private String severity;
    private Instant startTime;
    private Instant endTime;
    private Map<String, String> labels;
    private Integer httpStatusCode;
    private boolean generateExcel; // Flag for Excel report
}
