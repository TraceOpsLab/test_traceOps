package com.example.LogReader.service;

import com.example.LogReader.client.GeminiClient;
import com.example.LogReader.dto.GeminiAnalysisResutDto;
import com.example.LogReader.dto.LogKey;
import com.example.LogReader.entity.LogTable;
import com.example.LogReader.repository.LogTableRepository;
import com.example.LogReader.utils.JsonTrimmer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeminiAnalyzerService {

    private static final String PROMPT_PREFIX
            = "Please analyse below Java Stack Trace and List down Fully Qualified Exception class name, Possible root cause and Possible Solution in two lines. Output should be in the JSON format with keys exception, rootCause, solution\n\n\n";

    Map<LogKey, Integer> frequencyMap = new HashMap<>();
    Map<LogKey, LogTable> uniqueEntries = new HashMap<>();

    private final LogTableRepository logTableRepository;
    private final GeminiClient geminiClient;
    private final ObjectMapper objectMapper;

    public List<GeminiAnalysisResutDto> analyzeLogsFromAI() {

        uniqueEntries.clear();

        List<GeminiAnalysisResutDto> response = Lists.newArrayList();

        logTableRepository.findAll().stream().forEach(entry -> {
            //TODO: need to check parameters for uniqueness.
            LogKey key = new LogKey(UUID.randomUUID().toString(), entry.getSeverity(), entry.getLogName());
            //TODO: need to check parameters for uniqueness.
            frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + 1);
            uniqueEntries.putIfAbsent(key, entry);
        });

        if(MapUtils.isEmpty(uniqueEntries)) {
            throw new RuntimeException("No Logs Available to analyze.  Please hit the /persist method to generate logs");
        }

        uniqueEntries.values().forEach(entry -> {

            String prompt = PROMPT_PREFIX + entry.getMessage();

            String geminiResponse = JsonTrimmer.extractJson(geminiClient.analyzeError(prompt));

            try {
                response.add(objectMapper.readValue(geminiResponse, GeminiAnalysisResutDto.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        });

        return response;
    }
}
