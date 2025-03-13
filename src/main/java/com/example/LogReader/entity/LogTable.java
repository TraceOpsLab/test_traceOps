package com.example.LogReader.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@Table(name = "logs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LogTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logName;
    private String severity;
    @Lob
    private String message;
    private Instant timestamp;
}
