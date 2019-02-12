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

package org.madawa.genetics.bean;

/**
 * The class {@link ScheduledClass} represents a class/course that needs to be scheduled.
 */
public class ScheduledClass {
    private final int id;
    private final StudentGroup studentGroup;
    private final Module module;
    private Professor professor;
    private TimeSlot timeSlot;
    private Classroom classroom;

    public ScheduledClass(int id, StudentGroup studentGroup, Module module) {
        this.id = id;
        this.module = module;
        this.studentGroup = studentGroup;
    }

    public int getId() {
        return id;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public Module getModule() {
        return module;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
