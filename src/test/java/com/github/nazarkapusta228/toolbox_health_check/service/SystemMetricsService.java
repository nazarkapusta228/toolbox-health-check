package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.SystemMetrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SystemMetricsServiceTest {

    @Test
    void testSystemMetricsService() {

        SystemMetricsService service = new SystemMetricsService();

        SystemMetrics result = service.checkSystemMetrics();


        assertNotNull(result);
        assertNotNull(result.getDiskFree());
        assertNotNull(result.getMemoryFree());
        assertTrue(result.getCpuUsage() >= 0.0);

        System.out.println("System metrics check passed:");
        System.out.println("Disk: " + result.getDiskFree());
        System.out.println("Memory: " + result.getMemoryFree());
        System.out.println("CPU: " + result.getCpuUsage() + "%");
    }
}