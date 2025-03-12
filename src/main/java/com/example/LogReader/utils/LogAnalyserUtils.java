package com.example.LogReader.utils;

import com.example.LogReader.dto.LogKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.logging.LogEntry;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class LogAnalyserUtils {


    public static void generateExcelReport(Map<LogKey, LogEntry> uniqueEntries,
                                           Map<LogKey, Integer> frequencyMap) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Log Analysis");

        // Create headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Error Message");
        headerRow.createCell(1).setCellValue("Frequency");
        headerRow.createCell(2).setCellValue("Service Name");
        headerRow.createCell(3).setCellValue("HTTP Status");

        // Populate data
        int rowNum = 1;
        for (Map.Entry<LogKey, LogEntry> entry : uniqueEntries.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            LogKey key = entry.getKey();
            LogEntry logEntry = entry.getValue();

            row.createCell(0).setCellValue(key.getMessage());
            row.createCell(1).setCellValue(frequencyMap.get(key));
            row.createCell(2).setCellValue(logEntry.getResource().getLabels().get("service_name"));
            row.createCell(3).setCellValue(getDataFromLogJsonPayload(logEntry,"status_code"));
        }

        // Save to file
        try (FileOutputStream outputStream = new FileOutputStream("LogAnalysis.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel report", e);
        }
    }

    public static String getDataFromLogJsonPayload(LogEntry logEntry, String key){
        //JsonObject jsonPayload = JsonParser.parseString(logEntry.getPayload().toString()).getAsJsonObject();
       // return jsonPayload.get(key).toString();
        return "200";
    }


}
