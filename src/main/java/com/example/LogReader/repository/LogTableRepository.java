package com.example.LogReader.repository;

import com.example.LogReader.entity.LogTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogTableRepository extends JpaRepository<LogTable, Long> {
}
