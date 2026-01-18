package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthStatusServiceTest {

    @Mock
    private DockerHealthService dockerService;

    @Mock
    private GitStatusService gitService;

    @Mock
    private SystemMetricsService systemService;

    @InjectMocks
    private HealthStatusService healthStatusService;

    @Test
    void testGetOverallStats() {

        DockerHealth mockDocker = new DockerHealth(true, "20.10.0", 2, true, "Linux");
        GitStatus mockGit = new GitStatus(true, "main", "abc123");
        SystemMetrics mockSystem = new SystemMetrics("50 GB", "2 GB", 25.5);


        when(dockerService.checkDocker()).thenReturn(mockDocker);
        when(gitService.checkGitStatus()).thenReturn(mockGit);
        when(systemService.checkSystemMetrics()).thenReturn(mockSystem);


        HealthStatus result = healthStatusService.getOverallStats();


        assertNotNull(result);
        assertEquals("UP", result.getOverallStatus());
        assertNotNull(result.getTimestamp());
        assertTrue(result.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));


        Map<String, Object> services = result.getStatusOfServices();
        assertEquals(3, services.size());
        assertEquals(mockDocker, services.get("docker"));
        assertEquals(mockGit, services.get("git"));
        assertEquals(mockSystem, services.get("system"));


        verify(dockerService, times(1)).checkDocker();
        verify(gitService, times(1)).checkGitStatus();
        verify(systemService, times(1)).checkSystemMetrics();
    }
}