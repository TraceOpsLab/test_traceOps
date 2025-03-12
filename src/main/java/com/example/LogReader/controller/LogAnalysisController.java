package com.example.LogReader.controller;

import com.example.LogReader.dto.AnalysisResultDTO;
import com.example.LogReader.dto.FilterDTO;
import com.example.LogReader.entity.LogAnalysisResult;
import com.example.LogReader.repository.LogAnalysisRepository;
import com.example.LogReader.response.AnalysisResponse;
import com.example.LogReader.service.LogAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
public class LogAnalysisController {

    private final LogAnalyzerService logAnalyzerService;
    private final LogAnalysisRepository repository;

    @Autowired
    public LogAnalysisController(LogAnalyzerService logAnalyzerService,
                                 LogAnalysisRepository repository) {
        this.logAnalyzerService = logAnalyzerService;
        this.repository = repository;
    }

    @PostMapping("/persist")
    public ResponseEntity<AnalysisResponse> analyzeLogs(@RequestBody FilterDTO filterDTO) {
        // Process logs and persist to DB
        logAnalyzerService.processLogs(filterDTO);

        // Retrieve analysis results for response
        List<AnalysisResultDTO> results = repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        String excelStatus = filterDTO.isGenerateExcel() ? "Excel generated" : "Excel not requested";

        AnalysisResponse response = new AnalysisResponse("Analysis completed successfully", results, excelStatus);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/results")
    public ResponseEntity<List<AnalysisResultDTO>> getAnalysisResults() {
        List<AnalysisResultDTO> results = repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    private AnalysisResultDTO convertToDto(LogAnalysisResult entity) {
        return new AnalysisResultDTO(
                entity.getMessage(),
                entity.getServiceName(),
                entity.getClassName(),
                entity.getHttpStatusCode(),
                entity.getFrequency(),
                entity.getGeminiAnalysis()
        );
    }
}
