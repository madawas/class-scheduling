package org.iit.genetics.bean;

import java.util.List;

/**
 * The class StudentGroup represents a group of students which need to follow a set of enrollments.
 */
public class StudentGroup {
    private int id;
    private int size;
    private List<Module> enrollments;

    public void setId(int id) {
        this.id = id;
    }


    public void setSize(int size) {
        this.size = size;
    }
    public void setEnrollments(List<Module> enrollments) {
        this.enrollments = enrollments;
    }


    public int getId() {
        return this.id;
    }

    public int getSize() {
        return this.size;
    }

    public List<Module> getEnrollments() {
        return this.enrollments;
    }
}
