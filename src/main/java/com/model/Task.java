package com.model;

import javafx.beans.property.*;

/**
 * Frontend task model with simple properties for binding.
 */
public class Task {
    private final StringProperty id = new SimpleStringProperty();
    private final ObjectProperty<java.time.LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty subject = new SimpleStringProperty();
    private final DoubleProperty hours = new SimpleDoubleProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final BooleanProperty done = new SimpleBooleanProperty(false);

    public Task(String id, java.time.LocalDate date, String subject, double hours, String title, boolean done) {
        this.id.set(id);
        this.date.set(date);
        this.subject.set(subject);
        this.hours.set(hours);
        this.title.set(title);
        this.done.set(done);
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }

    public java.time.LocalDate getDate() { return date.get(); }
    public ObjectProperty<java.time.LocalDate> dateProperty() { return date; }
    public Task withDate(java.time.LocalDate d) {
        return new Task(getId(), d, getSubject(), getHours(), getTitle(), isDone());
    }

    public String getSubject() { return subject.get(); }
    public StringProperty subjectProperty() { return subject; }

    public double getHours() { return hours.get(); }
    public DoubleProperty hoursProperty() { return hours; }

    public String getTitle() { return title.get(); }
    public StringProperty titleProperty() { return title; }

    public boolean isDone() { return done.get(); }
    public BooleanProperty doneProperty() { return done; }
    public void setDone(boolean v) { done.set(v); }
}