package com.github.nazarkapusta228.toolbox_health_check.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GitStatusTest {

    @Test
    void testGitStatus() {
        GitStatus gitStatus = GitStatus.builder()
                .repoExists(true)
                .branch("main")
                .lastCommit("abc123")
                .build();

        assertTrue(gitStatus.isRepoExists());
        assertEquals("main", gitStatus.getBranch());
        assertEquals("abc123", gitStatus.getLastCommit());
    }
}