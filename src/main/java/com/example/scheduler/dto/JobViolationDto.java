package com.example.scheduler.dto;

import java.util.List;

public class JobViolationDto {

    private String jobName;
    private List<String> hardViolations;
    private List<String> softViolations;

    public JobViolationDto() {}

    public JobViolationDto(String jobName,
                           List<String> hard,
                           List<String> soft) {
        this.jobName = jobName;
        this.hardViolations = hard;
        this.softViolations = soft;
    }

    public String getJobName() { return jobName; }
    public List<String> getHardViolations() { return hardViolations; }
    public List<String> getSoftViolations() { return softViolations; }

    public void setJobName(String jobName) { this.jobName = jobName; }
    public void setHardViolations(List<String> hardViolations) { this.hardViolations = hardViolations; }
    public void setSoftViolations(List<String> softViolations) { this.softViolations = softViolations; }
}