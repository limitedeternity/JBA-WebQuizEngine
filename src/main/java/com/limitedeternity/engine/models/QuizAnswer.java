package com.limitedeternity.engine.models;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class QuizAnswer {
    @NotNull(message = "Answer field is required")
    private Set<Integer> answer;

    public QuizAnswer() { }

    public Set<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(Set<Integer> answer) {
        this.answer = answer;
    }
}
