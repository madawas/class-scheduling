package org.iit.genetics.main;

import org.iit.genetics.algorithm.Individual;
import org.iit.genetics.bean.Classroom;
import org.iit.genetics.bean.Module;
import org.iit.genetics.bean.Professor;
import org.iit.genetics.bean.ScheduledClass;
import org.iit.genetics.bean.StudentGroup;
import org.iit.genetics.bean.TimeSlot;
import org.iit.genetics.configuration.AppConfig;
import org.iit.genetics.configuration.AppData;
import org.iit.genetics.util.Util;

import java.util.List;

public class Timetable {
    private AppData appData;
    private AppConfig appConfig;
    private ScheduledClass[] scheduledClasses;
    private List<TimeSlot> timeSlots;
    private int classesToSchedule = 0;

    public Timetable(AppData appData, AppConfig appConfig) {
        this.appData = appData;
        this.appConfig = appConfig;
    }

    public Timetable(Timetable cloneable) {
        this.appData = cloneable.getAppData();
        this.timeSlots = cloneable.getTimeSlots();
        this.appConfig = cloneable.getAppConfig();
    }

    public void createClasses(Individual individual) {
        // Init scheduledClasses
        this.scheduledClasses = new ScheduledClass[this.getClassesToSchedule()];
        // Get individual's chromosome
        int[] chromosome = individual.getChromosome();
        int chromosomePos = 0;
        int classIndex = 0;

        for (StudentGroup studentGroup : this.appData.getStudentGroups()) {
            List<Module> modules = studentGroup.getEnrollments();
            for (Module module : modules) {
                this.scheduledClasses[classIndex] = new ScheduledClass(classIndex, studentGroup, module);

                // Add timeSlot
                this.scheduledClasses[classIndex].setTimeSlot(getTimeSlotById(chromosome[chromosomePos]));
                chromosomePos++;

                // Add room
                this.scheduledClasses[classIndex].setClassroom(getClassroom(chromosome[chromosomePos]));
                chromosomePos++;

                // Add professor
                this.scheduledClasses[classIndex].setProfessor(getProfessor(chromosome[chromosomePos]));
                chromosomePos++;

                classIndex++;
            }
        }
    }

    private Classroom getClassroom(int id) {
        return this.appData.getClassrooms().stream().filter(classroom -> classroom.getId() == id).findFirst()
                .orElse(null);
    }

    public Classroom getRandomClassroom() {
        int index = (int) (this.appData.getClassrooms().size() * Math.random());
        return this.appData.getClassrooms().get(index);
    }

    private Professor getProfessor(int id) {
        return this.appData.getProfessors().stream().filter(professor -> professor.getId() == id).findFirst()
                .orElse(null);
    }

    private TimeSlot getTimeSlotById(int id) {
        return this.timeSlots.stream().filter(timeSlot -> timeSlot.getId() == id).findFirst().orElse(null);
    }

    public TimeSlot getRandomTimeSlot() {
        int index = (int) (this.timeSlots.size() * Math.random());
        return timeSlots.get(index);
    }

    ScheduledClass[] getScheduledClasses() {
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

    public int calculateBlockers() {
        int blockers = 0;

        blockers += Util.calcRoomClashes(this.scheduledClasses);
        blockers += Util.calcRoomCapacityMismatches(this.scheduledClasses);
        blockers += Util.calcProfessorAvailability(this.scheduledClasses);
        blockers += (int) Math.ceil(Util.calcFollowOn(this.scheduledClasses) / appConfig.getWeightFollowOnClasses());

        return blockers;
    }

    public AppData getAppData() {
        return appData;
    }

    private List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    private AppConfig getAppConfig() {
        return appConfig;
    }
}
