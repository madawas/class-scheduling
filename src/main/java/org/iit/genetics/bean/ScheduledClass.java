package org.iit.genetics.bean;

/**
 * The class {@link ScheduledClass} represents a class/course that needs to be scheduled.
 */
public class ScheduledClass {
    private final int id;
    private final Batch batch;
    private final Module module;
    private Professor professor;
    private TimeSlot timeSlot;
    private Classroom classroom;

    public ScheduledClass(int id, Batch batch, Module module) {
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

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
