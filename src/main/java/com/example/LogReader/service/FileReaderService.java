package com.example.LogReader.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

@Service
public class FileReaderService {

    public String readFile(String fileName) throws IOException {
        Path path = Paths.get(new ClassPathResource(fileName).getURI());
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}
