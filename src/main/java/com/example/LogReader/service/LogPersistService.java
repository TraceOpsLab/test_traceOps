package com.example.LogReader.service;

import com.example.LogReader.converter.FilterConverter;
import com.example.LogReader.dto.FilterDTO;
import com.example.LogReader.entity.LogTable;
import com.example.LogReader.repository.LogTableRepository;
import com.google.api.gax.paging.Page;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LogPersistService {

    private final Logging logging;
    private final LogTableRepository logTableRepository;

    public void persistLogs(FilterDTO filter) {

        List<LogTable> logEntries = Lists.newArrayList();

        // 1. Convert DTO to GCP filter
        String gcpFilter = FilterConverter.toGcpFilter(filter);

        // 2. Fetch logs from GCP
        Page<LogEntry> entries = logging.listLogEntries(Logging.EntryListOption.filter(gcpFilter), Logging.EntryListOption.pageSize(10));

        entries.iterateAll().forEach(entry -> {
            if(Objects.isNull(entry.getPayload())){
                System.out.printf("No Logs Available");
            }else{
                System.out.printf("Timestamp: %s | Log: %s%n", entry.getInstantTimestamp(), entry.getPayload().toString());
                LogTable logTable = LogTable.builder()
                        .logName(entry.getLogName())
                        .message(entry.getPayload().getData().toString())
                        .severity(entry.getSeverity().toString())
                        .timestamp(entry.getInstantTimestamp())
                        .build();
                logEntries.add(logTable);
            }
        });

        //Persist to Log table.
        logTableRepository.saveAll(logEntries);
    }
}
