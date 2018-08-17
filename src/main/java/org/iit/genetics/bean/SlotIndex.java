package org.iit.genetics.bean;

import java.util.HashMap;
import java.util.Map;

public enum SlotIndex {
    SLOT_1(1, "8.00 - 10.00"), SLOT_2(2, "10.00 - 12.00"), SLOT_3(3, "13.00 - 15.00"), SLOT_4(4, "15.00 - 17.00");

    private String time;
    private int slot;
    private static final Map<Integer, SlotIndex> map;
    private static final Map<String, SlotIndex> map2;

    SlotIndex(int slot, String time) {
        this.slot = slot;
        this.time = time;
    }

    static {
        map = new HashMap<>();
        map2 = new HashMap<>();
        for (SlotIndex index : SlotIndex.values()) {
            map.put(index.slot, index);
            map2.put(index.time, index);
        }
    }

    public static SlotIndex findByKey(int i) {
        return map.get(i);
    }

    public static SlotIndex findByTime(String time) {
        return map2.get(time);
    }

    public String getTime() {
        return time;
    }

    public int getSlot() {
        return slot;
    }
}
