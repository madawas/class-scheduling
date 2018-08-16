package org.iit.genetics.bean;

public class TimeSlot {
    private final int id;
    private Weekday weekday;
    private SlotIndex slotIndex;

    public TimeSlot(int id, int weekday, int slotIndex){
        this.id = id;
        this.weekday = Weekday.findByKey(weekday);
        this.slotIndex = SlotIndex.findByKey(slotIndex);
    }

    public int getId(){
        return this.id;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public SlotIndex getSlotIndex() {
        return slotIndex;
    }
}
