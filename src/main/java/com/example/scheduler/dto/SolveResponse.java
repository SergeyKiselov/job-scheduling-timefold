package com.example.scheduler.dto;

import com.example.scheduler.domain.Schedule;
import java.util.List;

public class SolveResponse {

    private Schedule before;
    private Schedule after;
    private List<JobViolationDto> violations;
    private String scoreExplanation;

    public SolveResponse() {}

    public SolveResponse(Schedule before, Schedule after,
                         List<JobViolationDto> violations,
                         String scoreExplanation) {
        this.before = before;
        this.after = after;
        this.violations = violations;
        this.scoreExplanation = scoreExplanation;
    }

    public Schedule getBefore() { return before; }
    public Schedule getAfter() { return after; }
    public List<JobViolationDto> getViolations() { return violations; }
    public String getScoreExplanation() { return scoreExplanation; }

    public void setBefore(Schedule before) { this.before = before; }
    public void setAfter(Schedule after) { this.after = after; }
    public void setViolations(List<JobViolationDto> violations) { this.violations = violations; }
    public void setScoreExplanation(String scoreExplanation) { this.scoreExplanation = scoreExplanation; }
}