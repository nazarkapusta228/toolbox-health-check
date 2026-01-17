package com.github.nazarkapusta228.toolbox_health_check.service;

import com.github.nazarkapusta228.toolbox_health_check.model.GitStatus;
import org.springframework.stereotype.Service;

@Service
public class GitStatusService {
    public GitStatus checkGitStatus(){
        return GitStatus.builder()
                .repoExists(true)
                .lastCommit("last commit")
                .branch("main")
                .build();
    }
}
