package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.SystemMetrics;
import org.springframework.stereotype.Service;

@Service
public class SystemMetricsService {
    public SystemMetrics checkSystemMetrics(){
        return SystemMetrics.builder()
                .diskFree("10 GB")
                .memoryFree("100 GB")
                .cpuUsage(10)
                .build();
    }
}
