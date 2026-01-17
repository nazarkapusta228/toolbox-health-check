package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.DockerHealth;
import com.github.nazarkapusta228.toolbox_health_check.model.GitStatus;
import com.github.nazarkapusta228.toolbox_health_check.model.HealthStatus;
import com.github.nazarkapusta228.toolbox_health_check.model.SystemMetrics;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class HealthStatusService {
    private final DockerHealthService dockerService;
    private final GitStatusService gitService;
    private final SystemMetricsService systemService;

    public HealthStatusService(DockerHealthService dockerService,
                               GitStatusService gitService,
                               SystemMetricsService systemService){
        this.dockerService  = dockerService;
        this.gitService = gitService;
        this.systemService  = systemService;
    }

    public HealthStatus getOverallStats(){
        DockerHealth docker = dockerService.checkDocker();
        GitStatus gitStatus = gitService.checkGitStatus();
        SystemMetrics systemMetrics = systemService.checkSystemMetrics();

        Map<String, Object> services = Map.of(
                "docker", docker,
                "git", gitStatus,
                "system", systemMetrics
        );
        String overallStatus = "UP";
        return HealthStatus.builder()
                .overallStatus(overallStatus)
                .timestamp(LocalDateTime.now())
                .statusOfServices(services)
                .build();

    }
}
