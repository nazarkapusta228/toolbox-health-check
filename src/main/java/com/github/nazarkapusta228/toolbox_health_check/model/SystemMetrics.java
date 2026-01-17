package com.github.nazarkapusta228.toolbox_health_check.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SystemMetrics {
    private String diskFree;
    private String memoryFree;
    private double cpuUsage;
}
