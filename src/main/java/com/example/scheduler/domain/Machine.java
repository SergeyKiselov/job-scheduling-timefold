package com.example.scheduler.domain;

import java.util.Objects;

public class Machine {

    private String id;
    private String name;
    private int availableFrom;
    private int availableTo;

    public Machine() {
    }

    public Machine(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Machine(String id, String name, int availableFrom, int availableTo) {
        this.id = id;
        this.name = name;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
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

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(int availableFrom) {
        this.availableFrom = availableFrom;
    }

    public int getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(int availableTo) {
        this.availableTo = availableTo;
    }

    @Override
    public String toString() {
        return name != null && !name.isEmpty() ? name : id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return Objects.equals(id, machine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}