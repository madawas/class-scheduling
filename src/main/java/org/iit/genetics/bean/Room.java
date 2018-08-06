package org.iit.genetics.bean;

/**
 * The class {@link Room} represents a class room which is identified by a room number and has a capacity
 */
public class Room {
    private final int id;
    private final String number;
    private final int capacity;

    public Room(int id, String number, int capacity) {
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

    public int getRoomCapacity() {
        return this.capacity;
    }
}
