package com.example.LogReader.repository;

import com.example.LogReader.entity.LogAnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAnalysisRepository extends JpaRepository<LogAnalysisResult, Long> {}
