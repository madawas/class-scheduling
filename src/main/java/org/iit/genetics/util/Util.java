package org.iit.genetics.util;

import org.iit.genetics.bean.Classroom;
import org.iit.genetics.bean.Professor;
import org.iit.genetics.bean.ScheduledClass;
import org.iit.genetics.bean.SlotIndex;
import org.iit.genetics.bean.TimeSlot;
import org.iit.genetics.bean.Weekday;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {

    public static int calcRoomClashes(ScheduledClass[] scheduledClasses) {
        int unavailability = 0;

        for (ScheduledClass scheduledClass : scheduledClasses) {
            TimeSlot timeSlot = scheduledClass.getTimeSlot();
            Classroom classroom = scheduledClass.getClassroom();

            if (classroom.getUnavailability() != null) {
                for (Map.Entry<String, List<String>> entry : classroom.getUnavailability().entrySet()) {
                    String key = entry.getKey();
                    if (timeSlot.getWeekday().equals(Weekday.valueOf(key))) {
                        for (String time : entry.getValue()) {
                            if (timeSlot.getSlotIndex().equals(SlotIndex.findByTime(time))) {
                                unavailability++;
                            }
                        }
                    }
                }
            }
        }

        for (ScheduledClass scheduledClass : scheduledClasses) {
            // Check if room is taken
            for (ScheduledClass scheduledClassB : scheduledClasses) {
                if (scheduledClass.getClassroom() == scheduledClassB.getClassroom()
                        && scheduledClass.getTimeSlot() == scheduledClassB.
                        getTimeSlot() && scheduledClass.getId() != scheduledClassB.getId()) {
                    unavailability++;
                    break;
                }
            }
        }

        return unavailability;
    }

    public static int calcRoomCapacityMismatches(ScheduledClass[] scheduledClasses) {
        int mismatches = 0;

        for (ScheduledClass scheduledClass : scheduledClasses) {
            if (scheduledClass.getClassroom().getCapacity() < scheduledClass.getStudentGroup().getSize()) {
                mismatches++;
            }
        }

        return mismatches;
    }

    public static int calcProfessorAvailability(ScheduledClass[] scheduledClasses) {
        int unavailability = 0;
        // Check if professor is available
        for (ScheduledClass scheduledClass : scheduledClasses) {
            TimeSlot timeSlot = scheduledClass.getTimeSlot();
            Professor professor = scheduledClass.getProfessor();

            if (professor.getUnavailability() != null) {
                for (Map.Entry<String, List<String>> entry : professor.getUnavailability().entrySet()) {
                    String key = entry.getKey();
                    if (timeSlot.getWeekday().equals(Weekday.valueOf(key))) {
                        for (String time : entry.getValue()) {
                            if (timeSlot.getSlotIndex().equals(SlotIndex.findByTime(time))) {
                                unavailability++;
                            }
                        }
                    }
                }
            }
        }

        for (ScheduledClass scheduledClassA : scheduledClasses) {
            for (ScheduledClass scheduledClassB : scheduledClasses) {
                if (scheduledClassA.getProfessor() == scheduledClassB.
                        getProfessor() && scheduledClassA.getTimeSlot().equals(scheduledClassB.getTimeSlot())
                        && scheduledClassA.getId() != scheduledClassB.getId()) {
                    unavailability++;
                    break;
                }
            }
        }

        return unavailability;
    }

    public static int calcFollowOn(ScheduledClass[] scheduledClasses) {
        int followOnClashes = 0;
        List<String> checkedProfessors = new ArrayList<>();

        for (ScheduledClass scheduledClass : scheduledClasses) {
            int currentFollowOnClasses = 0;
            Professor professor = scheduledClass.getProfessor();
            if (checkedProfessors.contains(professor.getName()) || professor.getMaxFollowOnClasses() == -1) {
                continue;
            }

            for (ScheduledClass scheduledClass1 : scheduledClasses) {
                if (scheduledClass.getTimeSlot().getWeekday().equals(scheduledClass1.getTimeSlot().getWeekday())
                        && scheduledClass.getTimeSlot().getSlotIndex().getSlot()
                        == scheduledClass1.getTimeSlot().getSlotIndex().getSlot() - 1) {
                    currentFollowOnClasses++;
                }
            }

            if (currentFollowOnClasses > professor.getMaxFollowOnClasses()) {
                followOnClashes++;
            }

            checkedProfessors.add(professor.getName());
        }

        return followOnClashes;
    }
}
