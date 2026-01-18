package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.DockerHealth;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class DockerHealthService {

    public DockerHealth checkDocker() {
        try {

            boolean dockerInstalled = isDockerInstalled();

            if (!dockerInstalled) {
                return createErrorResponse("Docker is not installed");
            }


            boolean daemonRunning = isDockerDaemonRunning();


            String version = getDockerVersion();


            int containerCount = getContainerCount();

            return DockerHealth.builder()
                    .status(true)
                    .version(version)
                    .containerCount(containerCount)
                    .daemonRunning(daemonRunning)
                    .operatingSystem(getOperatingSystem())
                    .build();

        } catch (Exception e) {
            return createErrorResponse("Error: " + e.getMessage());
        }
    }

    private boolean isDockerInstalled() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"docker", "--version"});
            String output = readProcessOutput(process);
            return output.contains("Docker version") || output.contains("Docker Desktop");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDockerDaemonRunning() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"docker", "info"});
            String output = readProcessOutput(process);
            return output.contains("Server Version") || output.contains("Containers:");
        } catch (Exception e) {
            return false;
        }
    }

    private String getDockerVersion() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"docker", "--version"});
            String output = readProcessOutput(process);


            if (output.contains("Docker version")) {
                String[] parts = output.split(" ");
                if (parts.length >= 3) {

                    return parts[2].replace(",", "");
                }
            }
            return "Unknown version";
        } catch (Exception e) {
            return "Version check failed";
        }
    }

    private int getContainerCount() {
        try {

            Process process = Runtime.getRuntime().exec(new String[]{"docker", "ps", "-q"});
            String output = readProcessOutput(process);

            if (output.trim().isEmpty()) {
                return 0;
            }


            String[] lines = output.split("\n");
            int count = 0;
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    private String getOperatingSystem() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"docker", "info", "--format", "{{.OperatingSystem}}"});
            String output = readProcessOutput(process).trim();

            if (!output.isEmpty()) {
                return output;
            }


            Process infoProcess = Runtime.getRuntime().exec(new String[]{"docker", "info"});
            String infoOutput = readProcessOutput(infoProcess);

            if (infoOutput.contains("Operating System:")) {
                String[] lines = infoOutput.split("\n");
                for (String line : lines) {
                    if (line.contains("Operating System:")) {
                        return line.replace("Operating System:", "").trim();
                    }
                }
            }

            return "Unknown OS";
        } catch (Exception e) {
            return "OS detection failed";
        }
    }

    private String readProcessOutput(Process process) {
        StringBuilder output = new StringBuilder();
        try {
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
                if (reader.ready()) {
                    output.append("\n");
                }
            }
        } catch (Exception e) {

        }
        return output.toString();
    }

    private DockerHealth createErrorResponse(String message) {
        return DockerHealth.builder()
                .status(false)
                .version(message)
                .containerCount(0)
                .daemonRunning(false)
                .operatingSystem("unknown")
                .build();
    }
}