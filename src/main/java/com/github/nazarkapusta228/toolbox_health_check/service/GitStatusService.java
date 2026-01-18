package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.GitStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class GitStatusService {

    public GitStatus checkGitStatus() {
        try {

            boolean isGitRepo = checkIfGitRepository();

            if (!isGitRepo) {
                return GitStatus.builder()
                        .repoExists(false)
                        .lastCommit("Not a Git repository")
                        .branch("unknown")
                        .build();
            }


            String currentBranch = getCurrentBranch();


            String lastCommit = getLastCommit();


            return GitStatus.builder()
                    .repoExists(true)
                    .branch(currentBranch.isEmpty() ? "unknown" : currentBranch)
                    .lastCommit(lastCommit.isEmpty() ? "No commits yet" : lastCommit)
                    .build();

        } catch (Exception e) {
            return GitStatus.builder()
                    .repoExists(false)
                    .lastCommit("Error: " + e.getMessage())
                    .branch("error")
                    .build();
        }
    }

    private boolean checkIfGitRepository() {
        try {

            boolean hasGitDir = Files.exists(Paths.get(".git"));


            if (!hasGitDir) {
                Process process = Runtime.getRuntime().exec(new String[]{"git", "rev-parse", "--is-inside-work-tree"});
                String output = readProcessOutput(process);
                return output.trim().equals("true");
            }

            return hasGitDir;
        } catch (Exception e) {
            return false;
        }
    }

    private String getCurrentBranch() {
        try {

            Process process = Runtime.getRuntime().exec(new String[]{"git", "branch", "--show-current"});
            return readProcessOutput(process).trim();
        } catch (Exception e) {
            try {

                Process process = Runtime.getRuntime().exec(new String[]{"git", "rev-parse", "--abbrev-ref", "HEAD"});
                return readProcessOutput(process).trim();
            } catch (Exception ex) {
                return "";
            }
        }
    }

    private String getLastCommit() {
        try {

            Process process = Runtime.getRuntime().exec(new String[]{"git", "log", "-1", "--oneline"});
            return readProcessOutput(process).trim();
        } catch (Exception e) {
            return "";
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
}