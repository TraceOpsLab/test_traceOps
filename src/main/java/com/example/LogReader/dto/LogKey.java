package com.example.LogReader.dto;

import com.google.cloud.logging.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class LogKey {
    private final String message;
    private final String severity;
    private final String resourceType;
}