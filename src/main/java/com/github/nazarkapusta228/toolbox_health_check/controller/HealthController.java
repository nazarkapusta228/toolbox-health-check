package com.github.nazarkapusta228.toolbox_health_check.controller;


import com.github.nazarkapusta228.toolbox_health_check.model.DockerHealth;
import com.github.nazarkapusta228.toolbox_health_check.model.GitStatus;
import com.github.nazarkapusta228.toolbox_health_check.model.HealthStatus;
import com.github.nazarkapusta228.toolbox_health_check.model.SystemMetrics;
import com.github.nazarkapusta228.toolbox_health_check.service.DockerHealthService;
import com.github.nazarkapusta228.toolbox_health_check.service.GitStatusService;
import com.github.nazarkapusta228.toolbox_health_check.service.HealthStatusService;
import com.github.nazarkapusta228.toolbox_health_check.service.SystemMetricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")

public class HealthController {

    private final HealthStatusService healthService;
    private final SystemMetricsService systemService;
    private final GitStatusService gitService;
    private final DockerHealthService dockerService;

    public HealthController(HealthStatusService healthStatusService,
                            SystemMetricsService systemMetricsService,
                            GitStatusService gitStatusService,
                            DockerHealthService dockerHealthService){
        this.healthService = healthStatusService;
        this.systemService = systemMetricsService;
        this.gitService = gitStatusService;
        this.dockerService = dockerHealthService;
    }


    @GetMapping
    public HealthStatus getOverallHealth() {
        return healthService.getOverallStats();
    }

    @GetMapping("/docker")
    public DockerHealth getDockerHealth() {
        return dockerService.checkDocker();
    }

    @GetMapping("/system")
    public SystemMetrics getSystemMetrics() {
        return systemService.checkSystemMetrics();
    }

    @GetMapping("/git")
    public GitStatus getGitStatus() {
        return gitService.checkGitStatus();
    }
}
