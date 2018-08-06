package org.iit.genetics.bean;

public class TimeSlot {
    private final int id;
    private int day;
    private int slotIndex;

    public TimeSlot(int id, int day, int slotIndex){
        this.id = id;
        this.day = day;
        this.slotIndex = slotIndex;
    }

    public int getId(){
        return this.id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(byte day) {
        this.day = day;
    }

    public int getSlotIndex() {
        return slotIndex;
    }
}
