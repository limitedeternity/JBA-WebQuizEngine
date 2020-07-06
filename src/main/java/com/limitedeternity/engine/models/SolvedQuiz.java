package com.limitedeternity.engine.models;

import java.time.LocalDateTime;

public class SolvedQuiz {
    private Long id;
    private String completedAt = LocalDateTime.now().toString();

    public SolvedQuiz() {}
    public SolvedQuiz(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
