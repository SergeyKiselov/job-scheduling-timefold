package com.example.scheduler.dto;

public class SolverProgress {
    private long stepIndex;
    private String score;
    private long timeSpentMs;

    public SolverProgress() {
    }

    public SolverProgress(long stepIndex, String score, long timeSpentMs) {
        this.stepIndex = stepIndex;
        this.score = score;
        this.timeSpentMs = timeSpentMs;
    }

    // Геттеры
    public long getStepIndex() {
        return stepIndex;
    }

    public String getScore() {
        return score;
    }

    public long getTimeSpentMs() {
        return timeSpentMs;
    }

    // Сеттеры
    public void setStepIndex(long stepIndex) {
        this.stepIndex = stepIndex;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setTimeSpentMs(long timeSpentMs) {
        this.timeSpentMs = timeSpentMs;
    }

    @Override
    public String toString() {
        return "Step " + stepIndex + ": " + score + " (" + timeSpentMs + "ms)";
    }
}