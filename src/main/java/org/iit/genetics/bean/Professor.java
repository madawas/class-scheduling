package org.iit.genetics.bean;

import java.util.List;
import java.util.Map;

public class Professor {
    private int id;
    private String name;
    private int maxFollowOnClasses;
    private Map<String, List<String>>  unavailability;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, List<String>> getUnavailability() {
        return unavailability;
    }

    public void setUnavailability(Map<String, List<String>> unavailability) {
        this.unavailability = unavailability;
    }

    public int getMaxFollowOnClasses() {
        return maxFollowOnClasses;
    }

    public void setMaxFollowOnClasses(int maxFollowOnClasses) {
        this.maxFollowOnClasses = maxFollowOnClasses;
    }
}
