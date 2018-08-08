package org.iit.genetics.configuration;

import org.iit.genetics.bean.Classroom;
import org.iit.genetics.bean.StudentGroup;
import org.iit.genetics.bean.Module;
import org.iit.genetics.bean.Professor;

import java.util.List;

public class AppData {
    private List<Classroom> classrooms;
    private List<Professor> professors;
    private List<Module> modules;
    private List<StudentGroup> studentGroups;

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public void setResourceIds() {
        int id = 1;
        for (Classroom classroom : this.classrooms) {
            classroom.setId(id);
            ++id;
        }

        id = 1;

        for (Professor professor: this.professors) {
            professor.setId(id);
        }

        id = 1;

        for (Module module: this.modules) {
            module.setId(id);
        }

        id = 1;

        for (StudentGroup studentGroup : this.studentGroups) {
            studentGroup.setId(id);
        }
    }

}
