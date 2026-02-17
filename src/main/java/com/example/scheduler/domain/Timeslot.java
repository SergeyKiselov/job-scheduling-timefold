package com.example.scheduler.domain;

import java.util.Objects;

public class Timeslot {

    private int startHour;
    private int endHour;

    // Tukšs konstruktors OptaPlanner
    public Timeslot() {
    }

    // Konstruktors ar parametriem
    public Timeslot(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    // ===== Getters un Setters =====

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    // Metode, ko izmanto Schedule.java
    public String getStartTime() {
        return startHour + ":00";
    }

    // Ērta intervāla izvade
    @Override
    public String toString() {
        return startHour + ":00-" + endHour + ":00";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return startHour == timeslot.startHour && endHour == timeslot.endHour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startHour, endHour);
    }
}