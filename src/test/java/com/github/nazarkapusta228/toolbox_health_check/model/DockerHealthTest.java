package com.github.nazarkapusta228.toolbox_health_check.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DockerHealthTest {

    @Test
    void testDockerHealthBuilder() {

        DockerHealth health = DockerHealth.builder()
                .status(true)
                .version("20.10.0")
                .containerCount(3)
                .daemonRunning(true)
                .operatingSystem("Linux")
                .build();

        assertTrue(health.isStatus());
        assertEquals("20.10.0", health.getVersion());
        assertEquals(3, health.getContainerCount());
        assertTrue(health.isDaemonRunning());
        assertEquals("Linux", health.getOperatingSystem());
    }

    @Test
    void testDockerHealthSettersAndGetters() {
        DockerHealth health = new DockerHealth();
        health.setStatus(false);
        health.setVersion("not installed");
        health.setContainerCount(0);
        health.setDaemonRunning(false);
        health.setOperatingSystem("unknown");

        assertFalse(health.isStatus());
        assertEquals("not installed", health.getVersion());
        assertEquals(0, health.getContainerCount());
        assertFalse(health.isDaemonRunning());
        assertEquals("unknown", health.getOperatingSystem());
    }
}