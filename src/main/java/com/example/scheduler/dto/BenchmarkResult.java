package com.example.scheduler.dto;

public class BenchmarkResult {

    private String algorithm;
    private int hardScore;
    private int softScore;
    private long durationMs;
    private long totalSteps;        // реальное количество шагов алгоритма (из логов)
    private long improvements;       // количество улучшений (new best solutions)
    private long improvementSpeed;    // улучшений в секунду
    private long scoreCalcSpeed;      // score calculation speed (из логов)
    private String firstImprovement;
    private String lastImprovement;

    public BenchmarkResult() {
    }

    public BenchmarkResult(String algorithm, int hardScore, int softScore,
                           long durationMs, long totalSteps, long improvements,
                           long improvementSpeed, long scoreCalcSpeed,
                           String firstImprovement, String lastImprovement) {
        this.algorithm = algorithm;
        this.hardScore = hardScore;
        this.softScore = softScore;
        this.durationMs = durationMs;
        this.totalSteps = totalSteps;
        this.improvements = improvements;
        this.improvementSpeed = improvementSpeed;
        this.scoreCalcSpeed = scoreCalcSpeed;
        this.firstImprovement = firstImprovement;
        this.lastImprovement = lastImprovement;
    }

    // Геттеры
    public String getAlgorithm() {
        return algorithm;
    }

    public int getHardScore() {
        return hardScore;
    }

    public int getSoftScore() {
        return softScore;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public long getTotalSteps() {
        return totalSteps;
    }

    public long getImprovements() {
        return improvements;
    }

    public long getImprovementSpeed() {
        return improvementSpeed;
    }

    public long getScoreCalcSpeed() {
        return scoreCalcSpeed;
    }

    public String getFirstImprovement() {
        return firstImprovement;
    }

    public String getLastImprovement() {
        return lastImprovement;
    }

    // Сеттеры
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setHardScore(int hardScore) {
        this.hardScore = hardScore;
    }

    public void setSoftScore(int softScore) {
        this.softScore = softScore;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    public void setTotalSteps(long totalSteps) {
        this.totalSteps = totalSteps;
    }

    public void setImprovements(long improvements) {
        this.improvements = improvements;
    }

    public void setImprovementSpeed(long improvementSpeed) {
        this.improvementSpeed = improvementSpeed;
    }

    public void setScoreCalcSpeed(long scoreCalcSpeed) {
        this.scoreCalcSpeed = scoreCalcSpeed;
    }

    public void setFirstImprovement(String firstImprovement) {
        this.firstImprovement = firstImprovement;
    }

    public void setLastImprovement(String lastImprovement) {
        this.lastImprovement = lastImprovement;
    }
}