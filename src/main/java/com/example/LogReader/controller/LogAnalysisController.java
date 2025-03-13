package com.example.LogReader.controller;

import com.example.LogReader.dto.AnalysisResultDTO;
import com.example.LogReader.dto.FilterDTO;
import com.example.LogReader.dto.GeminiAnalysisResutDto;
import com.example.LogReader.entity.LogAnalysisResult;
import com.example.LogReader.entity.LogTable;
import com.example.LogReader.repository.LogAnalysisRepository;
import com.example.LogReader.repository.LogTableRepository;
import com.example.LogReader.service.GeminiAnalyzerService;
import com.example.LogReader.service.LogAnalyzerService;
import com.example.LogReader.service.LogPersistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class LogAnalysisController {

    private final LogAnalyzerService logAnalyzerService;
    private final LogAnalysisRepository repository;
    private final LogPersistService logPersistService;
    private final LogTableRepository logTableRepository;
    private final GeminiAnalyzerService geminiAnalyzerService;

    @PostMapping("/persist")
    public ResponseEntity<String> analyzeLogs(@RequestBody FilterDTO filterDTO) {
        // Process logs and persist to DB
        logPersistService.persistLogs(filterDTO);
        return ResponseEntity.ok("Logs Persisted to Log Table Successfully");
    }

    @GetMapping("/ai/result")
    public ResponseEntity<List<GeminiAnalysisResutDto>> viewAnalysis() {
        return ResponseEntity.ok(geminiAnalyzerService.analyzeLogsFromAI());
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
