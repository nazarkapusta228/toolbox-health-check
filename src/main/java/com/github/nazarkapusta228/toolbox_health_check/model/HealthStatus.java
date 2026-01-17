package com.github.nazarkapusta228.toolbox_health_check.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HealthStatus {
    private String overallStatus;
    private LocalDateTime timestamp;
    Map<String, Object> statusOfServices = new HashMap<>();
}
