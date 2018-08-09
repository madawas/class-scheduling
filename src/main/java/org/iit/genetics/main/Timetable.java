package org.iit.genetics.main;

import org.iit.genetics.algorithm.Individual;
import org.iit.genetics.bean.Classroom;
import org.iit.genetics.bean.Module;
import org.iit.genetics.bean.Professor;
import org.iit.genetics.bean.ScheduledClass;
import org.iit.genetics.bean.StudentGroup;
import org.iit.genetics.bean.TimeSlot;
import org.iit.genetics.configuration.AppData;

import java.util.List;

public class Timetable {
    private AppData appData;
    private ScheduledClass[] scheduledClasses;
    private List<TimeSlot> timeSlots;
    private int classesToSchedule = 0;

    public Timetable(AppData appData) {
        this.appData = appData;
    }

    public Timetable(Timetable cloneable) {
        this.appData = cloneable.getAppData();
        this.timeSlots = cloneable.getTimeSlots();
    }

    public void createClasses(Individual individual) {
        // Init scheduledClasses
        ScheduledClass[] scheduledClasses = new ScheduledClass[this.getClassesToSchedule()];
        // Get individual's chromosome
        int[] chromosome = individual.getChromosome();
        int chromosomePos = 0;
        int classIndex = 0;

        for (StudentGroup studentGroup : this.appData.getStudentGroups()) {
            List<Module> modules = studentGroup.getEnrollments();
            for (Module module : modules) {
                scheduledClasses[classIndex] = new ScheduledClass(classIndex, studentGroup, module);

                // Add timeSlot
                scheduledClasses[classIndex].setTimeSlot(getTimeSlotById(chromosome[chromosomePos]));
                chromosomePos++;

                // Add room
                scheduledClasses[classIndex].setClassroom(getClassroom(chromosome[chromosomePos]));
                chromosomePos++;

                // Add professor
                scheduledClasses[classIndex].setProfessor(getProfessor(chromosome[chromosomePos]));
                chromosomePos++;

                classIndex++;
            }
        }
        this.scheduledClasses = scheduledClasses;
    }

    public Classroom getClassroom(int id) {
        return this.appData.getClassrooms().stream().filter(classroom -> classroom.getId() == id).findFirst()
                .orElse(null);
    }

    public Classroom getRandomClassroom() {
        int index = (int) (this.appData.getClassrooms().size() * Math.random());
        return this.appData.getClassrooms().get(index);
    }

    public Professor getProfessor(int id) {
        return this.appData.getProfessors().stream().filter(professor -> professor.getId() == id).findFirst()
                .orElse(null);
    }

    public TimeSlot getTimeSlotById(int id) {
        return this.timeSlots.stream().filter(timeSlot -> timeSlot.getId() == id).findFirst().orElse(null);
    }

    public TimeSlot getRandomTimeSlot() {
        int index = (int) (this.timeSlots.size() * Math.random());
        return timeSlots.get(index);
    }

    public ScheduledClass[] getScheduledClasses() {
        return this.scheduledClasses;
    }

    public int getClassesToSchedule() {
        if (this.classesToSchedule > 0) {
            return this.classesToSchedule;
        }
        this.classesToSchedule = this.appData.getStudentGroups().stream()
                .mapToInt(studentGroup -> studentGroup.getEnrollments().size()).sum();
        return this.classesToSchedule;
    }

    public int calcClashes() {
        int clashes = 0;
        for (ScheduledClass scheduledClassA : this.scheduledClasses) {
            // Check room capacity
            int roomCapacity = scheduledClassA.getClassroom().getCapacity();
            int groupSize = scheduledClassA.getStudentGroup().getSize();
            if (roomCapacity < groupSize) {
                clashes++;
            }

            // Check if room is taken
            for (ScheduledClass scheduledClassB : this.scheduledClasses) {
                if (scheduledClassA.getClassroom() == scheduledClassB.getClassroom()
                        && scheduledClassA.getTimeSlot() == scheduledClassB.
                        getTimeSlot() && scheduledClassA.getId() != scheduledClassB.getId()) {
                    clashes++;
                    break;
                }
            }

            // Check if professor is available
            for (ScheduledClass scheduledClassB : this.scheduledClasses) {
                if (scheduledClassA.getProfessor() == scheduledClassB.
                        getProfessor() && scheduledClassA.getTimeSlot() == scheduledClassB.getTimeSlot()
                        && scheduledClassA.getId() != scheduledClassB.getId()) {
                    clashes++;
                    break;
                }
            }
        }

        return clashes;
    }

    public AppData getAppData() {
        return appData;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
