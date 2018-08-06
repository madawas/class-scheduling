package org.iit.genetics.bean;

/**
 * The class {@link Classroom} represents a class room which is identified by a room number and has a capacity
 */
public class Classroom {
    private final int id;
    private final String number;
    private final int capacity;

    public Classroom(int id, String number, int capacity) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
    }

    public int getId() {
        return this.id;
    }

    public String getNumber() {
        return this.number;
    }

    public int getClassroomCapacity() {
        return this.capacity;
    }
}
