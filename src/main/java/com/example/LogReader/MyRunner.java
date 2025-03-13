package com.example.LogReader;

import com.example.LogReader.dto.FilterDTO;
import com.example.LogReader.service.LogAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyRunner implements CommandLineRunner {

@Autowired
    private  LogAnalyzerService logAnalyzerService;
    @Override
    public void run(String... args) throws Exception {

/*
        logName="projects/my-project/logs/stdout"
        severity=WARNING
        resource.labels.service_name="order-service"
        resource.labels.region="us-central1"
        labels."logging.googleapis.com/trace"="projects/my-project/traces/123abc"
        jsonPayload.method="POST"
        jsonPayload.path="/api/v1/orders"
                */

        System.out.println("Application has started!");
        Map<String, String> labels = new HashMap<>();
        labels.put("service_name","demo-function-1");
        labels.put("location","us-central1");
        //labels.put("type ","cloud_run_revision");
        // Process logs and persist to DB
        //logAnalyzerService.processLogs(new FilterDTO(null,null,null,null,labels,null,true));

    }
}
