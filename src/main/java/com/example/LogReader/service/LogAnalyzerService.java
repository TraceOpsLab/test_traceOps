package com.example.LogReader.service;

import com.example.LogReader.client.GeminiClient;
import com.example.LogReader.converter.FilterConverter;
import com.example.LogReader.dto.FilterDTO;
import com.example.LogReader.dto.LogKey;
import com.example.LogReader.entity.LogAnalysisResult;
import com.example.LogReader.entity.LogTable;
import com.example.LogReader.repository.LogAnalysisRepository;
import com.example.LogReader.repository.LogTableRepository;
import com.google.api.gax.paging.Page;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.Severity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.example.LogReader.utils.LogAnalyserUtils.generateExcelReport;
import static com.example.LogReader.utils.LogAnalyserUtils.getDataFromLogJsonPayload;

@Service
@RequiredArgsConstructor
public class LogAnalyzerService {
    private final Logging logging;
    private final LogAnalysisRepository repository;
    private final GeminiClient geminiClient;
    private final LogTableRepository logTableRepository;

    Map<LogKey, Integer> frequencyMap = new HashMap<>();
    Map<LogKey, LogEntry> uniqueEntries = new HashMap<>();


    public void processLogs(FilterDTO filter) {
        // 1. Convert DTO to GCP filter
        String gcpFilter = FilterConverter.toGcpFilter(filter);
        System.out.println("......................");
        System.out.println("......................");
        System.out.println(gcpFilter);
        System.out.println("......................");
        System.out.println("......................");




        // 2. Fetch logs from GCP

        Page<LogEntry> entries = logging.listLogEntries(
                Logging.EntryListOption.filter(gcpFilter),
                Logging.EntryListOption.pageSize(1000)
        );


        logging.listLogEntries(Logging.EntryListOption.filter(gcpFilter)).iterateAll().forEach(entry -> {
                if(Objects.isNull(entry.getPayload())){
                    System.out.printf("No Logs Available");
                }else{
                    System.out.printf("Timestamp: %s | Log: %s%n", entry.getTimestamp(), entry.getPayload().toString());
                    LogKey key = new LogKey(entry.getPayload().toString(), entry.getSeverity().toString(), entry.getResource().getType());
                    LogTable logTable = LogTable.builder()
                            .logName(entry.getLogName())
                            .message(entry.getPayload().getData().toString())
                            .severity(entry.getSeverity().toString())
                            .timestamp(Instant.now())
                            .build();
                    logTableRepository.save(logTable);
                    frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + 1);
                    uniqueEntries.putIfAbsent(key, entry);
                }
        });


        // 3. Detect duplicates and extract metadata
        entries.iterateAll().forEach(entry -> {
            if(entry.getPayload()!=null){
                String message = entry.getPayload().toString();
                Severity severity = entry.getSeverity();
                String resourceType = entry.getResource().getType();
                LogKey key = new LogKey(message, severity.toString(), resourceType);
                uniqueEntries.merge(key, entry, (existing, replacement) -> existing);

            }
        });

        //TODO: this should be part of a separate API. Current API will only fetch logs and dump to the db.
        // 4. Analyze unique entries
        uniqueEntries.values().forEach(entry -> {
            LogAnalysisResult result = analyzeEntry(entry);
            repository.save(result);
            // Send to Gemini
            String analysis = geminiClient.analyzeError(entry.getPayload().getData().toString());
            System.out.println("Analysis ::: " + analysis);
            result.setGeminiAnalysis(analysis);
            repository.save(result);
        });

        if(!entries.hasNextPage()){
            System.out.println("Oops! no logs found...");
            //return;
        }


        // 5. Update Excel generation
        if (filter.isGenerateExcel()) {
            generateExcelReport(uniqueEntries, frequencyMap);
        }
    }

    private LogAnalysisResult analyzeEntry(LogEntry entry) {
        // Extract metadata (example - adjust based on your LogEntry structure)
        String serviceName = entry.getResource().getLabels().get("service_name");
        String className = entry.getLabels().get("class_name");
        Integer statusCode = Integer.parseInt(getDataFromLogJsonPayload(entry,"status_code"));

        LogAnalysisResult logAnalysisResult = new LogAnalysisResult();
        logAnalysisResult.setLogId(UUID.randomUUID().toString());
        if(frequencyMap.containsKey(entry)){
            logAnalysisResult.setFrequency(frequencyMap.get(entry));
        }
        logAnalysisResult.setServiceName(serviceName);
        logAnalysisResult.setClassName(className);
        logAnalysisResult.setHttpStatusCode(statusCode);
       // logAnalysisResult.setGeminiAnalysis(geminiClient.analyzeError(entry.toStructuredJsonString()));
        return logAnalysisResult;
    }
}