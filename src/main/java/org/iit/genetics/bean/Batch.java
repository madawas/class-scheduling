package org.iit.genetics.bean;

import java.util.List;

/**
 * The class Batch represents a group of students which need to follow a set of modules.
 */
public class Batch {
    private final int id;
    private final int size;
    private final List<Module> modules;

    public Batch(int id, int size, List<Module> modules) {
        this.id = id;
        this.size = size;
        this.modules = modules;
    }

    public int getId() {
        return this.id;
    }

    public int getSize() {
        return this.size;
    }

    public List<Module> getModules() {
        return this.modules;
    }
}
