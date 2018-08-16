package org.iit.genetics.bean;

import java.util.HashMap;
import java.util.Map;

public enum SlotIndex {
    SLOT_1(1, "8.00 - 10.00"), SLOT_2(2, "10.00 - 12.00"), SLOT_3(3, "13.00 - 15.00"), SLOT_4(4, "15.00 - 17.00");

    private String time;
    private int slot;
    private static final Map<Integer, SlotIndex> map;

    SlotIndex(int slot, String time) {
        this.slot = slot;
        this.time = time;
    }

    static {
        map = new HashMap<>();
        for (SlotIndex index : SlotIndex.values()) {
            map.put(index.slot, index);
        }
    }

    public static SlotIndex findByKey(int i) {
        return map.get(i);
    }

    public String getTime() {
        return time;
    }

    public int getSlot() {
        return slot;
    }
}
