package org.iit.genetics.bean;

import java.util.List;

public class Module {
    private final int id;
    private final String name;
    private final List<Professor> professors;

    public Module(int id, String name, List<Professor> professors) {
        this.id = id;
        this.name = name;
        this.professors = professors;
    }

    public int getId() {
        return this.id;
    }

    public String getModuleName() {
        return this.name;
    }

    public Professor getRandomProfessor() {
        return professors.get((int) (professors.size() * Math.random()));
    }
}
