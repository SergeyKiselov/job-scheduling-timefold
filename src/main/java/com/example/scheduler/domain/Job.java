package com.example.scheduler.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.Objects;

@PlanningEntity
public class Job {

    @PlanningId
    private String id;

    private String name;
    private int duration;
    private int dueDate;      // soft termiņš - nokavējuma aprēķināšanai
    private int deadline;      // hard termiņš - obligāti jāpabeidz līdz
    private int priority;

    private Machine machine;
    private Timeslot timeslot;

    private Machine preferredMachine;

    private int startTime;

    public Job() { }

    // Atjaunināts konstruktors ar deadline
    public Job(String id, String name, int duration, int dueDate, int deadline, int priority) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.dueDate = dueDate;
        this.deadline = deadline;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getDueDate() {
        return dueDate;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    @PlanningVariable(valueRangeProviderRefs = "machineRange")
    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
        if (timeslot != null) {
            this.startTime = timeslot.getStartHour();
        }
    }

    public Machine getPreferredMachine() {
        return preferredMachine;
    }

    public void setPreferredMachine(Machine preferredMachine) {
        this.preferredMachine = preferredMachine;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getFinishTime() {
        return startTime + duration;
    }

    // Nokavējums tiek skaitīts no dueDate (soft)
    public int getLateness() {
        return Math.max(0, getFinishTime() - dueDate);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}