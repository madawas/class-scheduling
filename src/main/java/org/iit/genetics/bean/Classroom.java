package org.iit.genetics.bean;

/**
 * The class {@link Classroom} represents a class room which is identified by a room number and has a capacity
 */
public class Classroom {
    private int id;
    private String number;
    private int capacity;

    public Classroom() {

    }

    public Classroom(int id, String number, int capacity) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
