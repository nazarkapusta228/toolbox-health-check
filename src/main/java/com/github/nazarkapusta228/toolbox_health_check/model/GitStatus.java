package com.github.nazarkapusta228.toolbox_health_check.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class GitStatus {
    private boolean repoExists;
    private String lastCommit;
    private String branch;
}
