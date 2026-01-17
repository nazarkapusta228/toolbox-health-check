package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.DockerHealth;
import org.springframework.stereotype.Service;

@Service
public class DockerHealthService {
    public DockerHealth checkDocker(){
        return DockerHealth.builder()
                .status(true)
                .version("29.1.3")
                .containerCount(3)
                .build();
    }
}
