package org.iit.genetics.bean;

public class Professor {
    private final int id;
    private final String name;

    public Professor(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
