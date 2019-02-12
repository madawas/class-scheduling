/*
 * Copyright (c) 2019 Madawa Soysa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.madawa.genetics.configuration;

import org.madawa.genetics.bean.Classroom;
import org.madawa.genetics.bean.Module;
import org.madawa.genetics.bean.Professor;
import org.madawa.genetics.bean.StudentGroup;

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

    void setResourceIds() {
        int id = 1;
        for (Classroom classroom : this.classrooms) {
            classroom.setId(id);
            ++id;
        }

        id = 1;

        for (Professor professor : this.professors) {
            professor.setId(id);
            ++id;
        }

        id = 1;

        for (Module module : this.modules) {
            module.setId(id);
            ++id;
        }

        id = 1;

        for (StudentGroup studentGroup : this.studentGroups) {
            studentGroup.setId(id);
            ++id;
        }
    }

}
