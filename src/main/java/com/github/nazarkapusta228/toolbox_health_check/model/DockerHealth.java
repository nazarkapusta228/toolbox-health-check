package com.github.nazarkapusta228.toolbox_health_check.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DockerHealth {
    private boolean status;
    private String version;
    private int containerCount;
    private boolean daemonRunning;
    private String operatingSystem;

}
