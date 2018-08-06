package org.iit.genetics.bean;

public class TimeSlot {
    private final int id;
    private final String timeSlot;

    public TimeSlot(int id, String timeSlot){
        this.id = id;
        this.timeSlot = timeSlot;
    }

    public int getId(){
        return this.id;
    }

    public String getTimeSlot(){
        return this.timeSlot;
    }
}
