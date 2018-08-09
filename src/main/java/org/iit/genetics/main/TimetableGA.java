package org.iit.genetics.main;

import org.iit.genetics.algorithm.Algorithm;
import org.iit.genetics.algorithm.Population;
import org.iit.genetics.bean.ScheduledClass;
import org.iit.genetics.bean.TimeSlot;
import org.iit.genetics.configuration.AppData;

import java.util.ArrayList;
import java.util.List;

public class TimetableGA {
    public static void main(String[] args) {

        Timetable timetable = initializeTimetable();
        // Initialize GA
        Algorithm ga = new Algorithm(100, 0.01, 0.9, 2, 5);

        Population population = ga.initPopulation(timetable);

        ga.evalPopulation(population, timetable);

        // Keep track of current generation
        int generation = 1;

        // Start evolution loop
        while (!ga.isTerminationConditionMet(generation, 1000) && !ga.isTerminationConditionMet(population)) {
            // Print fitness
            System.out.println("G" + generation + " Best fitness: " + population.getFittest(0).getFitness());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population, timetable);

            // Evaluate population
            ga.evalPopulation(population, timetable);

            // Increment the current generation
            generation++;
        }

        // Print fitness
        timetable.createClasses(population.getFittest(0));
        System.out.println();
        System.out.println("Solution found in " + generation + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).
                getFitness());
        System.out.println("Clashes: " + timetable.calcClashes());

        // Print scheduledClasses
        System.out.println();
        ScheduledClass[] scheduledClasses = timetable.getScheduledClasses();
        int classIndex = 1;

        for (ScheduledClass bestScheduledClass : scheduledClasses) {
            System.out.println("ScheduledClass " + classIndex + ":");
            System.out.println("Module: " + bestScheduledClass.getModule().getName());
            System.out.println("StudentGroup: " + bestScheduledClass.getStudentGroup().getId());
            System.out.println("Classroom: " + bestScheduledClass.getClassroom().getNumber());
            System.out.println("Professor: " + bestScheduledClass.getProfessor().getName());
            System.out.println("Time: " + bestScheduledClass.getTimeSlot().getDay() + " " + bestScheduledClass.getTimeSlot().getSlotIndex());
            System.out.println("-----");
            classIndex++;
        }
    }

    private static Timetable initializeTimetable() {
        // Create timetable
        Timetable timetable = new Timetable(new AppData());

        List<TimeSlot> timeSlots = new ArrayList<>();

        int id = 1;
        for (int day = 1; day <= 5; ++day) {
            for (int slot = 1; slot <= 3; ++slot) {
                timeSlots.add(new TimeSlot(id, day, slot));
                ++id;
            }
        }
        timetable.setTimeSlots(timeSlots);
        return timetable;
    }
}
