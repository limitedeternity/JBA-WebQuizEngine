package com.limitedeternity.engine.models;

public class QuizResult {
    private boolean success;
    private String feedback;

    public QuizResult() { }
    public QuizResult(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
