package com.example.LogReader.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;


@Entity
@Table(name = "logs")
class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logName;
    private String severity;
    @Lob
    private String message;
    private Instant timestamp;

    public LogEntity() {}

    public LogEntity(String logName, String severity, String message, Instant timestamp) {
        this.logName = logName;
        this.severity = severity;
        this.message = message;
        this.timestamp = timestamp;
    }
}
