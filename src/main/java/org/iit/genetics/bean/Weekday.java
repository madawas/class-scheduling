package org.iit.genetics.bean;

import java.util.HashMap;
import java.util.Map;

public enum Weekday {
    MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5);

    private final int day;
    private static final Map<Integer, Weekday> map;

    static {
        map = new HashMap<>();
        for (Weekday weekday : Weekday.values()) {
            map.put(weekday.day, weekday);
        }
    }

    public static Weekday findByKey(int i) {
        return map.get(i);
    }

    public int getDay() {
        return day;
    }

    Weekday(int day) {
        this.day = day;
    }
}
