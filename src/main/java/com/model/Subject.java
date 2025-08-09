package com.model;

import java.time.LocalDate;

/**
 * Simple subject model (frontend only).
 */
public class Subject {
    private final String name;
    private final int difficulty; // 1-5
    private final double targetHours;
    private final LocalDate examDate; // can be null

    public Subject(String name, int difficulty, double targetHours, LocalDate examDate) {
        this.name = name;
        this.difficulty = difficulty;
        this.targetHours = targetHours;
        this.examDate = examDate;
    }

    public String getName() { return name; }
    public int getDifficulty() { return difficulty; }
    public double getTargetHours() { return targetHours; }
    public LocalDate getExamDate() { return examDate; }

    @Override
    public String toString() {
        String d = examDate == null ? "no exam date" : examDate.toString();
        return name + " (D" + difficulty + ", " + targetHours + "h, " + d + ")";
    }
}