package org.iit.genetics.bean;

/**
 * The class {@link Class} represents a class/course that needs to be scheduled.
 */
public class Class {
    private final int id;
    private final Batch batch;
    private final Module module;
    private Professor professor;
    private TimeSlot timeSlot;
    private Room room;

    public Class(int id, Batch batch, Module module) {
        this.id = id;
        this.module = module;
        this.batch = batch;
    }

    public int getId() {
        return id;
    }

    public Batch getBatch() {
        return batch;
    }

    public Module getModule() {
        return module;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
