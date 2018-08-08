package org.iit.genetics.bean;

import java.util.List;

public class Module {
    private int id;
    private String name;
    private List<Professor> professors;

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

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public Professor getRandomProfessor() {
        return professors.get((int) (professors.size() * Math.random()));
    }
}
