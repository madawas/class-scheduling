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

package org.madawa.genetics.main;

import org.apache.log4j.Logger;
import org.madawa.genetics.bean.Classroom;
import org.madawa.genetics.bean.Professor;
import org.madawa.genetics.bean.ScheduledClass;
import org.madawa.genetics.bean.StudentGroup;
import org.madawa.genetics.bean.TimeSlot;
import org.madawa.genetics.util.Util;
import org.madawa.genetics.algorithm.Individual;
import org.madawa.genetics.bean.Module;
import org.madawa.genetics.configuration.AppConfig;
import org.madawa.genetics.configuration.AppData;

import java.util.List;

public class Timetable {
    private static final Logger log = Logger.getLogger(Timetable.class);
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
        if (log.isDebugEnabled()) {
            log.debug("Room Clashes: " + Util.calcRoomClashes(scheduledClasses));
        }
        blockers += Util.calcRoomCapacityMismatches(this.scheduledClasses);
        if (log.isDebugEnabled()) {
            log.debug("Room Capacity Clashes: " + Util.calcRoomClashes(this.scheduledClasses));
        }
        blockers += Util.calcProfessorAvailability(this.scheduledClasses);
        if (log.isDebugEnabled()) {
            log.debug("Professor Unavailability Clashes: " + Util.calcProfessorAvailability(this.scheduledClasses));
        }
        blockers += (int) Math.ceil(Util.calcFollowOn(this.scheduledClasses) / appConfig.getWeightFollowOnClasses());
        if (log.isDebugEnabled()) {
            log.debug("Follow On Classes Clashes: " + Util.calcFollowOn(this.scheduledClasses));
        }

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
